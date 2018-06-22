//
//  LeftViewController.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/5.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit
import RealmSwift
import Kingfisher


enum LeftMenu: Int {

    case all = 0
    case setting
}

protocol LeftMenuProtocol: class {
    func changeViewController(_ menu: LeftMenu)
}

class LeftViewController: UIViewController, LeftMenuProtocol {


    @IBOutlet weak var tableView: UITableView!
    var mainViewController: UIViewController!
    var settingController: UIViewController!
    var logindexControler: UIViewController!
    var userinfoControler: UIViewController!
    var menus = ["所有", "设置"]
    var imageHeaderView: ImageHeaderView!
    var userSetting: UserSetting!
    let realm = try! Realm()

    required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        self.tableView.separatorColor = UIColor(red: 224 / 255, green: 224 / 255, blue: 224 / 255, alpha: 1.0)

        let storyboard = UIStoryboard(name: "Main", bundle: nil)

        let SetttingViewController = storyboard.instantiateViewController(withIdentifier: "SetttingViewController")
        as! SetttingViewController

        let loginIndexViewController = storyboard.instantiateViewController(withIdentifier: "loginIndexViewController")
        as! loginIndexViewController

        let userinfoControler = storyboard.instantiateViewController(withIdentifier: "UserinfoViewController")
        as! UserinfoViewController

        self.settingController = UINavigationController(rootViewController: SetttingViewController)

        self.logindexControler = UINavigationController(rootViewController: loginIndexViewController)

        self.userinfoControler = UINavigationController(rootViewController: userinfoControler)

        self.tableView.registerCellClass(BaseTableViewCell.self)

        self.imageHeaderView = ImageHeaderView.loadNib()

        userSetting = realm.objects(UserSetting.self).first


//        imageHeaderView.profileClick = { () -> () in
//          print("test click")
//        }
        self.view.addSubview(self.imageHeaderView)
        let tap = UITapGestureRecognizer(target: self, action: #selector(LeftViewController.tapClick(_:)))

        imageHeaderView.isUserInteractionEnabled = true
        imageHeaderView.addGestureRecognizer(tap)

        // Do any additional setup after loading the view.
    }

    func tapClick(_ sender: UIView) {
        self.slideMenuController()?.changeMainViewController(self.mainViewController, close: true)


        if userSetting.isLogin {
            self.present(self.userinfoControler, animated: true, completion: nil)
          //  self.navigationController?.pushViewController(self.userinfoControler, animated: true)
        } else {
            self.present(self.logindexControler, animated: true, completion: nil)
           // self.navigationController?.pushViewController(self.logindexControler, animated: true)
        }
    }

    override func viewWillAppear(_ animated: Bool) {
        if userSetting.isLogin {
            let imageUrl = URL(string: "https://tva1.sinaimg.cn/crop.0.0.996.996.180/6e0c28fajw8f7ualn66qgj20ro0roq4q.jpg")
            self.imageHeaderView.profileImage.kf.setImage(with: imageUrl)
        } else {
            self.imageHeaderView.profileImage.image = #imageLiteral(resourceName:"default_photo_light")
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


    override func viewDidLayoutSubviews() {
        super.viewDidLayoutSubviews()
        self.imageHeaderView.frame = CGRect(x: 0, y: 0, width: self.view.frame.width, height: 160)
        self.view.layoutIfNeeded()
    }

    func changeViewController(_ menu: LeftMenu) {
        switch menu {
        case .all:
            self.slideMenuController()?.changeMainViewController(self.mainViewController, close: true)
        case .setting:
            self.slideMenuController()?.changeMainViewController(self.mainViewController, close: true)
            //  self.slideMenuController()?.changeMainViewController(self.settingController, close: true)
            self.present(self.settingController!, animated: true, completion: nil)
            self.navigationController?.pushViewController(self.settingController!, animated: true)
        }
    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */


}


extension LeftViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        if let menu = LeftMenu(rawValue: indexPath.row) {
            switch menu {
            case .all, .setting:
                return BaseTableViewCell.height()
            }
        }
        return 0
    }

    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if let menu = LeftMenu(rawValue: indexPath.row) {
            self.changeViewController(menu)
        }
    }

    func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if self.tableView == scrollView {

        }
    }
}

extension LeftViewController: UITableViewDataSource {

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return menus.count
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {

        if let menu = LeftMenu(rawValue: indexPath.row) {
            switch menu {
            case .all, .setting:
                let cell = BaseTableViewCell(style: UITableViewCellStyle.subtitle, reuseIdentifier: BaseTableViewCell.identifier)
                cell.setData(menus[indexPath.row])
                return cell
            }
        }
        return UITableViewCell()
    }
}

