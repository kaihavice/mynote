//
//  SetttingViewController.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/11/28.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit
import Moya
import RealmSwift

class SetttingViewController: UIViewController {
    let realm = try! Realm()


    @IBAction func logoutClick(_ sender: Any) {
        let user = self.realm.objects(User.self).first
        let userSetting = self.realm.objects(UserSetting.self).first
        let logoutProvider = MoyaProvider<NoteApi>(plugins: [NetworkLoggerPlugin(verbose: true),
                                                             AuthPlugin(token: user?.accessToken)])


        try! self.realm.safeWrite {
            userSetting?.isLogin = false
        }

        logoutProvider.request(.logout) { result in
            if case let .success(response) = result {

                self.ShowMessageAlert(msg: "退出登陆成功")

            }
        }
        self.navigationController!.popViewController(animated: true)
        dismiss(animated: true, completion: nil)
    }

    @IBOutlet weak var logoutButton: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()


        logoutButton.backgroundColor = UIColor(hex: "00c28b")
        logoutButton.layer.cornerRadius = 5
        logoutButton.setTitleColor(UIColor.white, for: .normal)
        logoutButton.setTitleColor(UIColor(hex: "00c28b"), for: .highlighted)

//        // Do any additional setup after loading the view.
//        let leftBarBtn = UIBarButtonItem(title: "返回", style: .plain, target: self,
//                                         action: #selector(backToPrevious))
//        self.navigationItem.leftBarButtonItem = leftBarBtn
    }

    override func viewWillAppear(_ animated: Bool) {
        self.removeNavigationBarItem()
        self.setBackNavigationBarItem()
        self.title = "设置"
    }

//    @objc override func backToPrevious(){
//        self.navigationController!.popViewController(animated: true)
//    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }


//    override func viewWillAppear(_ animated: Bool) {
//        super.viewWillAppear(animated)
//        self.setNavigationBarItem()
//    }

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
