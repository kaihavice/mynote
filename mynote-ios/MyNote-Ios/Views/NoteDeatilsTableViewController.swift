//
//  NoteDeatilsTableViewController.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/6.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit
import RealmSwift
import Moya
import Photos
import ObjectMapper

import Kingfisher

class NoteContentCell: UITableViewCell {

    @IBOutlet weak var uiTextview: UITextView!
}

class NoteCheckListCell: UITableViewCell {

    @IBOutlet weak var checkBox: CheckboxButton!
    @IBOutlet weak var checkText: UITextView!
}

class NotePhotoCell: UITableViewCell {

    @IBOutlet weak var imageview: UIImageView!
}


class NoteDeatilsTableViewController: UITableViewController, UITextViewDelegate,
        UIImagePickerControllerDelegate, UINavigationControllerDelegate {


    @IBOutlet var tabview: UITableView!

    @IBAction func didToggleCheckBoxButton(_ sender: CheckboxButton) {
        // let state = sender.on ? true : false
       

        try! realm.safeWrite {
        let check = checkList![sender.tag - 1]
                    if sender.on{
                        check.checked = true
                        print("check----> true")
                    }else{
                        check.checked = false
                        print("check----> false")
                    }
            if check.status == SyncStatus.HasSync {
                check.status = SyncStatus.LocalUpdate
                
            }
            if note.status == SyncStatus.HasSync {
                note.status = SyncStatus.LocalUpdate
                
            }
            userSetting.isHaveNew = true
            check.modifiedTime = self.getNowDataInt()
               note.modifiedTime = self.getNowDataInt()
        }

        // print("CheckboxButton:  \(sender.tag) did turn \(state)")
    }

    var segueNote: Note?
    var note: Note!
    var checkList: Results<CheckListItem>?
    var photoList: Results<AttachMent>?
    var unUploadAttachmentList: Results<AttachMent>?
    let realm = try! Realm()
    var noteContentCell: NoteContentCell!
    var checkListCell: NoteCheckListCell!
    var notePhotoCell: NotePhotoCell!
    var isNew = false

    var user: User!
    var userSetting: UserSetting!


    override func viewDidLoad() {
        super.viewDidLoad()


        self.tableView.tableFooterView = UIView(frame: CGRect.zero)

        userSetting = realm.objects(UserSetting.self).first
        user = realm.objects(User.self).first
//        tableView.estimatedRowHeight = 100
//        tableView.rowHeight = UITableViewAutomaticDimension
        initData()

    }


    func initData() {

        print(realm.configuration.fileURL ?? "")

        if segueNote == nil {

            isNew = true

            self.note = Note()

            let currentTime = self.getNowDataInt()
            note.createTime = currentTime
            note.modifiedTime = currentTime
            note.noteId = DataManger.creatUUid()
            note.status = SyncStatus.LocalNew
            note.deleted = false
            note.content = ""
            note.kind = Constant.TEXT

            let user = realm.objects(User.self).first

            if user == nil {
                note.userId = "0"
            } else {
                note.userId = user!.sid
            }

            try! realm.safeWrite {
                userSetting.isHaveNew = true
                realm.add(note, update: true)
            }

        } else {


            note = segueNote


        }

        checkList = realm.objects(CheckListItem.self).filter("noteId == %@", note.noteId)
        photoList = realm.objects(AttachMent.self).filter("noteId == %@", note.noteId)

        unUploadAttachmentList = realm.objects(AttachMent.self).filter("noteId == %@", note.noteId).filter("spath == %@", "")

        if unUploadAttachmentList != nil && self.userSetting.isLogin {
            for attachment in unUploadAttachmentList! {
                uploadFile(attachment)
            }
        }


        print("checkList ----> \(String(describing: checkList?.count))")
        print("photoList ----> \(String(describing: photoList?.count))")
    }


    func getCheckData() -> Results<CheckListItem> {
        return    realm.objects(CheckListItem.self).filter("noteId == %@", note.noteId)
    }

    func getPhoteoData() -> Results<AttachMent> {
        return    realm.objects(AttachMent.self).filter("noteId == %@", note.noteId)
    }

    func textViewDidChange(_ textView: UITextView) {


        if textView.tag == 255 {
            try! realm.safeWrite {
                note.modifiedTime = self.getNowDataInt()
                note.contentOld = note.content
                if note.status == SyncStatus.HasSync {
                    note.status = SyncStatus.LocalUpdate
                    
                }
                userSetting.isHaveNew = true
                note.content = textView.text!
                print("note 数据保存")

            }
        } else {
            try! realm.safeWrite {
                let check = checkList![textView.tag - 1]
                
                if note.status == SyncStatus.HasSync {
                    note.status = SyncStatus.LocalUpdate
                    
                }
                if check.status == SyncStatus.HasSync {
                    check.status = SyncStatus.LocalUpdate
                    
                }
                userSetting.isHaveNew = true
                check.modifiedTime = self.getNowDataInt()
                note.modifiedTime = self.getNowDataInt()
                check.title = textView.text!
                
                print("current check--->\(check.status)")

            }
        }

        let currentOffset = tableView.contentOffset
        UIView.setAnimationsEnabled(false)
        tableView.beginUpdates()
        tableView.endUpdates()
        UIView.setAnimationsEnabled(true)
        tableView.setContentOffset(currentOffset, animated: false)

    }


    func showNoteContent(noteContext: UITextView) {


        noteContext.setPaddingAndText(padding: 8, text: note.content, textSize: 15)


    }


    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
//        self.navigationController?.navigationBar.barTintColor = UIColor(hex:"00c55c")
//        self.navigationController?.navigationBar.tintColor = UIColor.white


        if !note.isInvalidated && note.content.isEmpty {


            try! self.realm.safeWrite {
                userSetting.isHaveNew = false
                if checkList == nil && photoList == nil {
                    self.realm.delete(self.note)
                } else if checkList?.count == 0 && photoList?.count == 0 {
                    self.realm.delete(self.note)
                }


            }

        }
    }


    func moreChoice() {
        let alertController = UIAlertController(title: nil, message: nil, preferredStyle: .actionSheet)

        let photoChoice = UIAlertAction(title: "照片", style: .default, handler: {
            action in
            let photoController = UIAlertController(title: nil, message: nil, preferredStyle: .actionSheet)

            let photoSelectChoice = UIAlertAction(title: "相册", style: .default, handler: {
                action in

                let picker = UIImagePickerController()
                picker.allowsEditing = false
                picker.sourceType = .photoLibrary
                picker.delegate = self

                self.present(picker, animated: true, completion: nil)

            })
            let camaroSelectChoice = UIAlertAction(title: "相机", style: .default, handler: {
                action in
                let picker = UIImagePickerController()
                picker.allowsEditing = false
                picker.sourceType = .camera
                picker.delegate = self

                self.present(picker, animated: true, completion: nil)
            })

            let cancelChoice = UIAlertAction(title: "取消", style: .cancel, handler: nil)

            photoController.addAction(photoSelectChoice)
            photoController.addAction(camaroSelectChoice)
            photoController.addAction(cancelChoice)

            self.present(photoController, animated: true, completion: nil)

        })

        let sendChoice = UIAlertAction(title: "发送", style: .default, handler: {
            action in
            let textToShare = self.note.content
            // let imageToShare: AnyObject = UIImage(named: "swift") as! AnyObject
            //  let urlToShare: AnyObject = NSURL(string: "http://m.baidu.com") as! AnyObject

            let activityViewController = UIActivityViewController(activityItems: [textToShare], applicationActivities: nil)

            self.present(activityViewController, animated: true, completion: nil)

            // 分享完成
            activityViewController.completionWithItemsHandler = { activityType, completed, returnedItems, activityError in

                // 分享完成或退出分享时调用该方法
                print("分享完成")
            }

        })

//        let fileInfoChoice = UIAlertAction(title: "详情", style: .default,handler: {
//            action in
//
//            let anotherVC = NoteInfoController()
//            //self.present(anotherVC, animated: true, completion: nil)
//            self.navigationController?.pushViewController(anotherVC, animated: true)
//
//        })

        let delteChoice = UIAlertAction(title: "删除", style: .destructive, handler: {
            action in


            try! self.realm.safeWrite {
                self.userSetting.isHavedelete = true
                if self.note.status == SyncStatus.HasSync {


                    self.note.status = SyncStatus.Delete
                    self.note.modifiedTime = self.getNowDataInt()
                    self.note.deleted = true

                } else {
                    self.note.deleted = true
                    self.realm.delete(self.note)

                    let checkListCount = self.checkList?.count

                    if self.checkList != nil && checkListCount! > 0 {
                        self.realm.delete(self.checkList!)
                    }

                    let photoListCount = self.photoList?.count

                    if self.photoList != nil && photoListCount! > 0 {
                        self.realm.delete(self.photoList!)
                    }
                }

            }


            // self.dismiss(animated: true, completion: nil)
            // self.performSegue(withIdentifier: "", sender: self)
            self.navigationController?.popViewController(animated: true)
        })

        let checkListChoice = UIAlertAction(title: "清单", style: .default, handler: {
            action in
            let check = CheckListItem()

            let currentTime = self.getNowDataInt()
            check.createTime = currentTime
            check.modifiedTime = currentTime
            check.status = SyncStatus.LocalNew
            check.deleted = false
            check.sid = UUID().uuidString.pregReplace(pattern: "[^a-zA-Z0-9]", with: "")
            check.noteId = self.note.noteId


            try! self.realm.safeWrite {
                if self.photoList?.count != 0 {
                    self.note.kind = Constant.CHECKLISTIMAGE
                } else {
                    self.note.kind = Constant.CHECKLIST
                }

                if self.note.status != SyncStatus.LocalNew {
                    try! self.realm.safeWrite {
                        self.note.status = SyncStatus.LocalUpdate
                        self.userSetting.isHaveNew = true
                    }
                }

                self.realm.add(check)
            }

            self.checkList = self.getCheckData()
            // self.tabview.reloadData()
            //  self.tabview.beginUpdates()

            self.tabview.insertRows(at: [IndexPath(row: self.checkList!.count, section: 0)], with: .automatic)
            //  self.tabview.endUpdates()


        })

        let cancelChoice = UIAlertAction(title: "取消", style: .cancel, handler: nil)

        alertController.addAction(photoChoice)
        alertController.addAction(checkListChoice)
        alertController.addAction(sendChoice)
        // alertController.addAction(fileInfoChoice)
        alertController.addAction(delteChoice)
        alertController.addAction(cancelChoice)

        self.present(alertController, animated: true, completion: nil)
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


    fileprivate func uploadFile(_ photo: AttachMent) {
        let uploadApi = MoyaProvider<NoteApi>(plugins: [
            AuthPlugin(token: self.user.accessToken)
            , NetworkLoggerPlugin(verbose: true)])


        uploadApi.request(.upload((photo.fileData?.base64EncodedString(options: NSData.Base64EncodingOptions(rawValue: 0)))!, "png", self.note.noteId)) { result in
            if case let .success(response) = result {

                let qiniuRepose = Mapper<BackData<QiniuRepose>>().map(JSONString: try! response.mapString())

               
                try! self.realm.safeWrite {
                    photo.spath = (qiniuRepose?.data?.fileUrl)!
                    photo.size = (qiniuRepose?.data?.fsize)!
                    self.realm.add(self.user, update: true)
                }
            }
        }
    }

    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [String: Any]) {
        let uiImage = info[UIImagePickerControllerOriginalImage] as? UIImage!


        let photo = AttachMent()
        photo.sid = UUID().uuidString.pregReplace(pattern: "[^a-zA-Z0-9]", with: "")
        photo.noteId = self.note.noteId
        let currentTime = self.getNowDataInt()
        photo.createTime = currentTime
        photo.modifiedTime = currentTime
photo.fileType = Constant.PHOTO
        photo.status = SyncStatus.LocalNew
        photo.deleted = false


        photo.fileData = ImageUtils.zipImage(currentImage: uiImage!!, scaleSize: 0.3, percent: 0.7) as Data


        try! self.realm.safeWrite {
            self.note.kind = Constant.PHOTO
            self.realm.add(photo)
        }

        if self.note.status != SyncStatus.LocalNew {
            try! self.realm.safeWrite {
                self.note.status = SyncStatus.LocalUpdate
                self.note.modifiedTime = self.getNowDataInt()
                userSetting.isHaveNew = true
            }
        }


        if self.userSetting.isLogin {
            uploadFile(photo)

        }


        self.photoList = getPhoteoData()

        self.tabview.insertRows(at: [IndexPath(row: self.checkList!.count + (self.photoList?.count)!, section: 0)], with: .automatic)

        // let imageData =    try! Data(uiImage:uiImage)
        dismiss(animated: true, completion: nil)
    }

    override func viewWillAppear(_ animated: Bool) {


        self.navigationController?.navigationBar.barTintColor = UIColor.white
        self.navigationController?.navigationBar.tintColor = UIColor(hex: "00c28b")


        let item = UIBarButtonItem(title: "", style: .plain, target: self, action: #selector(moreChoice))
        item.image = UIImage(text: Iconfont.moreIcon, fontSize: 15, imageSize: CGSize(width: 15, height: 15), imageColor: UIColor.white)

        self.navigationItem.rightBarButtonItem = item


    }

    // MARK: - Table view data source


    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of rows


        if (checkList == nil && photoList == nil) {
            return 1
        } else if (checkList == nil && photoList != nil) {
            return photoList!.count + 1
        } else if (checkList != nil && photoList == nil) {
            return checkList!.count + 1
        } else {
            return checkList!.count + photoList!.count + 1
        }

    }


    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {


        if indexPath.row == 0 {
            noteContentCell = tableView.dequeueReusableCell(withIdentifier: "noteContentCell") as? NoteContentCell


            noteContentCell.uiTextview.delegate = self
            noteContentCell.uiTextview.tintColor = UIColor(hex: "00c55c")
            if isNew {
                noteContentCell.uiTextview.becomeFirstResponder()
                isNew = false
            }
            showNoteContent(noteContext: noteContentCell.uiTextview)

            return noteContentCell
        } else if indexPath.row <= (checkList?.count)! {
            checkListCell = tableView.dequeueReusableCell(withIdentifier: "checkListCell") as? NoteCheckListCell


            checkListCell.checkText.tintColor = UIColor(hex: "00c55c")
            checkListCell.checkText.tag = indexPath.row
            checkListCell.checkBox.tag = indexPath.row
            checkListCell.checkText.delegate = self

            let check = checkList![indexPath.row - 1]


            checkListCell.checkText.text = check.title

            checkListCell.checkBox.on = check.checked


            return checkListCell
        } else {
            notePhotoCell = tableView.dequeueReusableCell(withIdentifier: "notePhotoCell") as? NotePhotoCell
            
            
            
            if photoList![indexPath.row - (checkList?.count)! - 1].fileData != nil {
                notePhotoCell.imageview.image = UIImage(data: photoList![indexPath.row - (checkList?.count)! - 1].fileData!)
               
            } else {
                let url = URL(string:  photoList![indexPath.row - (checkList?.count)! - 1].spath)
                print("spathh -----> \( photoList![indexPath.row - (checkList?.count)! - 1].spath)")
                 let imageUrl = URL(string: "https://tva1.sinaimg.cn/crop.0.0.996.996.180/6e0c28fajw8f7ualn66qgj20ro0roq4q.jpg")
                notePhotoCell.imageview.kf.setImage(with: url)
            }

            notePhotoCell.imageview.contentMode = .scaleAspectFill
            notePhotoCell.imageview.clipsToBounds = true


            print("curret photoList----> \(String(describing: photoList?.count))")
            print("curret photoList row----> \(indexPath.row - 1)")
            return notePhotoCell
        }
        //   print("执行到这里了2")

    }

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {

    }


    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableViewAutomaticDimension
    }

    override func tableView(_ tableView: UITableView, estimatedHeightForRowAt indexPath: IndexPath) -> CGFloat {
        return UITableViewAutomaticDimension
    }


    /*
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    override func tableView(_ tableView: UITableView, editingStyleForRowAt indexPath: IndexPath) -> UITableViewCellEditingStyle {
        if indexPath.row != 0 {
            return .delete
        } else {
            return .none
        }
    }


    // Override to support editing the table view.
    override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCellEditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            // Delete the row from the data source
            if indexPath.row == 0 {

            } else if indexPath.row <= (checkList?.count)! {


                try! realm.safeWrite {
                    note.status = SyncStatus.LocalUpdate
                    note.modifiedTime = self.getNowDataInt()
                    userSetting.isHaveNew = true
                    let check = checkList![indexPath.row - 1]
                    check.status = SyncStatus.Delete
                    check.deleted = true

                }

                realm.delete(checkList![indexPath.row - 1])
            } else {
                try! realm.safeWrite {
                    note.status = SyncStatus.LocalUpdate
                    note.modifiedTime = self.getNowDataInt()
                    userSetting.isHaveNew = true
                    let photo = photoList![indexPath.row - 1]
                    photo.status = SyncStatus.Delete
                    photo.deleted = true

                }

                realm.delete(photoList![indexPath.row - (checkList?.count)! - 1])
            }

            tableView.deleteRows(at: [indexPath], with: .fade)
        } else if editingStyle == .insert {
            print("insert====>")
            // self.tabview.insertRows(at: [indexPath], with: .automatic)
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }
        //  self.tabview.reloadData()
    }


//    override func tableView(_ tableView: UITableView, editActionsForRowAt indexPath: IndexPath) -> [UITableViewRowAction]? {
//
//    }

    /*
    // Override to support rearranging the table view.
    override func tableView(_ tableView: UITableView, moveRowAt fromIndexPath: IndexPath, to: IndexPath) {

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(_ tableView: UITableView, canMoveRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */


}
