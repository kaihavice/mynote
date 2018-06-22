//  MainViewController.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/5.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit
import SlideMenuControllerSwift
import Material
import RealmSwift
import ESPullToRefresh
import Moya
import ObjectMapper
import Kingfisher

class NoteCell: UITableViewCell {

    @IBOutlet weak var noteContent: UILabel!
    @IBOutlet weak var noteCheck: UILabel!
    @IBOutlet weak var noteTime: UILabel!
    @IBOutlet weak var noteImage: UIImageView!
    @IBOutlet weak var noteType: UIImageView!

}

class MainViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {


    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var fabButton: FABButton!
    var noteList: [Note]!
    var allNoteList: [Note]!
    var user: User!
    var userSetting: UserSetting!
    var logindexControler: UIViewController!
    let realm = try! Realm()
    var token: String?
    var page = 1
    var pageNum = 1

    override func viewDidLoad() {
        super.viewDidLoad()

        self.tableView!.delegate = self
        self.tableView!.dataSource = self


        fabButton.image = Icon.cm.add
        fabButton.backgroundColor = UIColor(hex: "00c28b")

        userSetting = realm.objects(UserSetting.self).first
        user = realm.objects(User.self).first

        allNoteList = self.realm.objects(Note.self).filter("deleted == %@", false)
                .sorted(byKeyPath: "modifiedTime", ascending: false).toArray(ofType: Note.self) as [Note]


        self.tableView.es.addPullToRefresh {
            [unowned self] in

            let listCount = self.allNoteList.count

            self.page = 1
            self.tableView.es.resetNoMoreData()

            if self.noteList == nil {
                self.noteList = [Note]()
            } else {
                self.noteList?.removeAll()
            }


            let index = listCount >= self.page * 10 ? self.page * 10 : listCount

            for i in 0..<index {

                self.noteList?.append(self.allNoteList[i])

            }


            self.tableView.reloadData()
            /// 如果你的刷新事件成功，设置completion自动重置footer的状态

            self.tableView.es.stopPullToRefresh(ignoreDate: true)
            //  self.automaticallyAdjustsScrollViewInsets = false


        }


        self.tableView.es.addInfiniteScrolling {
            [unowned self] in
            let listCount = self.allNoteList.count
            self.page = self.page + 1

            let index = listCount >= self.page * 10 ? self.page * 10 : listCount


            if (self.page - 1) * 10 < listCount {

                for i in (self.page - 1) * 10..<index {

                    self.noteList?.append(self.allNoteList[i])

                }

                self.tableView.reloadData()
                self.tableView.es.stopLoadingMore()
            } else {
                self.tableView.es.noticeNoMoreData()
            }
        }


        tableView.es.startPullToRefresh()


    }


    @objc override func rightMenu() {
        let MeunController = UIAlertController(title: nil, message: nil, preferredStyle: .actionSheet)

        let syncChoice = UIAlertAction(title: "同步", style: .default, handler: {
            action in
            if self.userSetting.isLogin {
                self.syncNoteData()
            } else {
                let storyboard = UIStoryboard(name: "Main", bundle: nil)
                let loginIndexViewController = storyboard.instantiateViewController(withIdentifier: "loginIndexViewController")
                as! loginIndexViewController
//                self.logindexControler = UIViewController(coder: loginIndexViewController)
                //           self.present( loginIndexViewController, animated: true, completion: nil)
                self.navigationController?.pushViewController(loginIndexViewController, animated: true)
            }


        })
        let printChoice = UIAlertAction(title: "指纹", style: .default, handler: {
            action in

        })

        let cancelChoice = UIAlertAction(title: "取消", style: .cancel, handler: nil)

        MeunController.addAction(syncChoice)
        MeunController.addAction(printChoice)
        MeunController.addAction(cancelChoice)

        self.present(MeunController, animated: true, completion: nil)
    }


    override func viewWillTransition(to size: CGSize, with coordinator: UIViewControllerTransitionCoordinator) {
        super.viewWillTransition(to: size, with: coordinator)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        self.navigationController?.navigationBar.barTintColor = UIColor(hex: "00c28b")
        self.navigationController?.navigationBar.tintColor = UIColor.white
        self.navigationController?.navigationBar.titleTextAttributes =
                [NSForegroundColorAttributeName: UIColor.white]
        self.title = "所有"
        self.setNavigationBarItem()

        if userSetting.isHaveNew || userSetting.isHavedelete {

            allNoteList = self.realm.objects(Note.self).filter("deleted == %@", false)
                    .sorted(byKeyPath: "modifiedTime", ascending: false).toArray(ofType: Note.self) as [Note]


            self.noteList?.removeAll()
            self.tableView.reloadData()


            tableView.es.startPullToRefresh()
            try! realm.safeWrite {
                userSetting.isHaveNew = false
                userSetting.isHavedelete = false
            }
        }


    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }

    @IBAction func close(segue: UIStoryboardSegue) {


    }


    @IBAction func refreshListData(segue: UIStoryboardSegue) {

    }


    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.

        if (segue.identifier == "noteId") {

            let dest = segue.destination as! NoteDeatilsTableViewController

            dest.segueNote = self.noteList![tableView.indexPathForSelectedRow!.row]
        }


    }


    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MyCell", for: indexPath) as! NoteCell
        let note = self.noteList![indexPath.row]
        // cell.tag = indexPath.row

//        cell.noteContent.text = note.content
        if (cell.noteContent.text?.isEmpty)! {
            cell.noteContent.text = "无标题"
        } else {
            cell.noteContent.setPaddingAndText(padding: 8, text: note.content, textSize: 13)
        }

        cell.noteContent.lineBreakMode = .byCharWrapping
        cell.noteContent.numberOfLines = 2


        let checkList = realm.objects(CheckListItem.self).filter("noteId == %@", note.noteId)
        let photoList = realm.objects(AttachMent.self).filter("noteId == %@", note.noteId)


        if checkList != nil && checkList.count > 0 {
            cell.noteCheck.text = "- " + checkList[0].title
        } else {
            cell.noteCheck.text = ""
        }

        cell.noteImage.image = nil


        if photoList != nil && photoList.count > 0 {


            if photoList[0].fileData != nil {
                cell.noteImage.image = UIImage(data: photoList[0].fileData!)
            } else {
                let url = URL(string: photoList[0].spath)


                cell.noteImage.kf.setImage(with: url)

            }


            cell.noteImage.contentMode = .scaleAspectFill
        }


        let dformatter = DateFormatter()
        dformatter.dateFormat = "MM-dd HH:mm"

        cell.noteTime.text = dformatter.string(from: Date(timeIntervalSince1970: Double(note.modifiedTime)))


        return cell
    }


    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {

        return noteList == nil ? 0 : self.noteList!.count
    }


    @IBAction func backToMain(_ segue: UIStoryboardSegue) {
        syncNoteData()


    }

    fileprivate func updateLocal(_ noteList: [Note]?) {

        RealmHelper.addListDataAsync(objects: noteList!)

        for note in noteList! {
            RealmHelper.addRealmListDataAsync(objects: note.attachmentList)
            RealmHelper.addRealmListDataAsync(objects: note.checkListItemList)
        }
    }


    func syncNoteData() {


        let addlist = selectAttachment(noteList: realm.objects(Note.self).filter("status == %@",
                SyncStatus.LocalNew).toArray(ofType: Note.self) as [Note])


        let updatelist = selectAttachment(noteList: realm.objects(Note.self).filter("status == %@",
                SyncStatus.LocalUpdate).toArray(ofType: Note.self) as [Note])


        let detelelist = selectAttachment(noteList: realm.objects(Note.self).filter("status == %@",
                SyncStatus.Delete).toArray(ofType: Note.self) as [Note])


        let requestApi = MoyaProvider<NoteApi>(plugins: [
            NoteSyncPlugin(checkPoint: token != nil ? 0 : user.lastSyncNoteTime, token: token != nil ? token : user.accessToken)
            , NetworkLoggerPlugin(verbose: true)])


        let requstNote = RequestNote(add: addlist, update: updatelist, delete: detelelist)

        var jsonStr: String!


        try! self.realm.safeWrite {
            jsonStr = requstNote.toJSONString(prettyPrint: true)

        }


        requestApi.request(.syncnote(jsonStr.convertToDictionary())) { result in
            if case let .success(response) = result {
                let syncNote = Mapper<BackData<SyncNoteData>>().map(JSONString: try! response.mapString())

                if (syncNote?.success)! {

                    let noteList = syncNote?.data?.syncNoteList


                    let user = self.realm.objects(User.self).first


                    try! self.realm.safeWrite {
                        user?.lastSyncNoteTime = (syncNote?.data?.checkpoint)!
                    }


                    for note in requstNote.add {
                        RealmHelper.addListDataAsync(objects: (note.requestAttachment?.add)!)
                        RealmHelper.addListDataAsync(objects: (note.requestCheck?.add)!)
                    }

                    for note in requstNote.update {
                        RealmHelper.addListDataAsync(objects: (note.requestAttachment?.add)!)
                        RealmHelper.addListDataAsync(objects: (note.requestAttachment?.delete)!)

                        RealmHelper.addListDataAsync(objects: (note.requestCheck?.add)!)
                        RealmHelper.addListDataAsync(objects: (note.requestCheck?.update)!)
                        RealmHelper.addListDataAsync(objects: (note.requestCheck?.delete)!)
                    }

                    for note in requstNote.delete {
                        RealmHelper.addListDataAsync(objects: (note.requestAttachment?.add)!)
                        RealmHelper.addListDataAsync(objects: (note.requestAttachment?.delete)!)

                        RealmHelper.addListDataAsync(objects: (note.requestCheck?.add)!)
                        RealmHelper.addListDataAsync(objects: (note.requestCheck?.update)!)
                        RealmHelper.addListDataAsync(objects: (note.requestCheck?.delete)!)
                    }


                    RealmHelper.addListDataAsync(objects: requstNote.add)
                    RealmHelper.addListDataAsync(objects: requstNote.update)
                    RealmHelper.addListDataAsync(objects: requstNote.delete)


                    DispatchQueue(label: "background").async {


                        if noteList != nil {
                            self.updateLocal(noteList)
                        }


                    }

                    if noteList != nil {

//                        if noteList?.count == 10 {
                        self.getNextWebPage()
                        // }
                    }


                } else {

                    if syncNote?.errCode == "M00009" {


                        try! self.realm.safeWrite {
                            self.userSetting.isLogin = false
                            self.user.lastSyncNoteTime = 0
                        }

                        let storyboard = UIStoryboard(name: "Main", bundle: nil)
                        let loginIndexViewController = storyboard.instantiateViewController(withIdentifier: "loginIndexViewController")
                        as! loginIndexViewController
                        self.navigationController?.pushViewController(loginIndexViewController, animated: true)


                        self.ShowMessageAlert(msg: "token错误 ，请重新登陆")


                    } else {
                        self.ShowMessageAlert(msg: (syncNote?.message)!)
                    }


                }
            }
        }


    }

    func getNextWebPage() {

        self.pageNum = self.pageNum + 1

        let noteListRequest = MoyaProvider<NoteApi>(plugins: [
            AuthPlugin(token: token != nil ? token : user.accessToken)
            , NetworkLoggerPlugin(verbose: true)])


        noteListRequest.request(.usernotelist(self.pageNum)) {
            result in
            if case let .success(response) = result {

                let noteListData = Mapper<BackData<NoteListData>>().map(JSONString: try! response.mapString())

                if (noteListData?.success!)! {

                    DispatchQueue(label: "background").async {

                        self.updateLocal(noteListData?.data?.noteList)

                    }

                    if (noteListData?.data?.ishaveNext)! {
                        self.getNextWebPage()
                    } else {

                        self.allNoteList = self.realm.objects(Note.self).filter("deleted == %@", false)
                                .sorted(byKeyPath: "modifiedTime", ascending: false).toArray(ofType: Note.self) as [Note]

                        self.noteList?.removeAll()
                        self.tableView.reloadData()

                        self.tableView.es.startPullToRefresh()

                    }
                }


            }
        }


    }


    func selectAttachment(noteList: [Note]) -> [Note] {
        for note in noteList {


            let addAttchmentList = realm.objects(AttachMent.self).filter("noteId == %@", note.noteId).filter("status == %@",
                    SyncStatus.LocalNew).toArray(ofType: AttachMent.self) as [AttachMent]

            let deteleAttchmentList = realm.objects(AttachMent.self).filter("noteId == %@", note.noteId).filter("status == %@",
                    SyncStatus.Delete).toArray(ofType: AttachMent.self) as [AttachMent]

            note.requestAttachment = RequestAttachment.init(add: addAttchmentList, delete: deteleAttchmentList)

            let addCheckList = realm.objects(CheckListItem.self).filter("noteId == %@", note.noteId).filter("status == %@",
                    SyncStatus.LocalNew).toArray(ofType: CheckListItem.self) as [CheckListItem]

            let updateCheckList = realm.objects(CheckListItem.self).filter("noteId == %@", note.noteId).filter("status == %@",
                    SyncStatus.LocalUpdate).toArray(ofType: CheckListItem.self) as [CheckListItem]

            let deteleCheckList = realm.objects(CheckListItem.self).filter("noteId == %@", note.noteId).filter("status == %@",
                    SyncStatus.Delete).toArray(ofType: CheckListItem.self) as [CheckListItem]


            note.requestCheck = RequestCheck.init(add: addCheckList, update: updateCheckList, delete: deteleCheckList)
        }

        return noteList
    }


}


extension MainViewController: SlideMenuControllerDelegate {

    func leftWillOpen() {
        print("SlideMenuControllerDelegate: leftWillOpen")
    }

    func leftDidOpen() {
        print("SlideMenuControllerDelegate: leftDidOpen")
    }

    func leftWillClose() {
        print("SlideMenuControllerDelegate: leftWillClose")
    }

    func leftDidClose() {
        print("SlideMenuControllerDelegate: leftDidClose")
        // self.tableView.es.stopPullToRefresh(ignoreDate: true)
    }

    func rightWillOpen() {
        print("SlideMenuControllerDelegate: rightWillOpen")
    }

    func rightDidOpen() {
        print("SlideMenuControllerDelegate: rightDidOpen")
    }

    func rightWillClose() {
        print("SlideMenuControllerDelegate: rightWillClose")
    }

    func rightDidClose() {
        print("SlideMenuControllerDelegate: rightDidClose")
    }


}


