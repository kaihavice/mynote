//
//  logInViewController.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/6.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit
import Moya
import ObjectMapper
import RealmSwift

class logInViewController: UIViewController, UITextFieldDelegate {
    func getToken(token: String) {
        
    }
    
    
    
    

    @IBOutlet weak var emailText: UITextField!
    @IBOutlet weak var loginButton: UIButton!
    @IBOutlet weak var passwordText: UITextField!

    @IBOutlet weak var forgetText: UILabel!
    let realm = try! Realm()
    var token: String?
    

    override func viewDidLoad() {
        super.viewDidLoad()


        emailText.delegate = self
        passwordText.delegate = self

        emailText.backgroundColor = UIColor(hex: "#f1f2f2")
        emailText.placeholder = "邮箱"
        emailText.clearButtonMode = .whileEditing
        emailText.keyboardType = UIKeyboardType.emailAddress
        passwordText.backgroundColor = UIColor(hex: "#f1f2f2")
        passwordText.placeholder = "密码"
        passwordText.clearButtonMode = .whileEditing
        emailText.becomeFirstResponder()
        passwordText.isSecureTextEntry = true
        loginButton.backgroundColor = UIColor(hex: "00c28b")
        loginButton.layer.cornerRadius = 5
        forgetText.textColor = UIColor(hex: "00c28b")

        loginButton.setTitleColor(UIColor.white, for: .normal)
        loginButton.setTitleColor(UIColor(hex: "00c28b"), for: .highlighted)
        // Do any additional setup after loading the view.
    }

    @IBAction func loginClick(_ sender: Any) {
        if emailText.text!.isEmpty {
            self.ShowMessageAlert(msg: "邮箱为空")
        }
        if passwordText.text!.isEmpty {
            self.ShowMessageAlert(msg: "密码为空")
        }

        MoyaProvider<NoteApi>(plugins: [NetworkLoggerPlugin(verbose: true)]).request(.link) { result in
            if case let .success(response) = result {

                let nonce = Mapper<BackData<Nonce>>().map(JSONString: try! response.mapString())

                if nonce != nil {
                    let md5Password = self.emailText.text! + ":" + (self.passwordText.text?.md5)! + ":" + (nonce?.data?.nonce)!
                    let loginProvider = MoyaProvider<NoteApi>(plugins: [
                        LoginAuthPlugin(randomToken: (nonce?.data?.randomToken)!)
                        , NetworkLoggerPlugin(verbose: true)])

                    loginProvider.request(.login(self.emailText.text!, md5Password.md5)) { result in
                        if case let .success(response) = result {

                            let userBean = Mapper<BackData<UserBeanBack>>().map(JSONString: try! response.mapString())


                            print(self.realm.configuration.fileURL ?? "")
                            if (userBean?.success)! {

                                let userList = self.realm.objects(User.self)
                                let userSetting = self.realm.objects(UserSetting.self).first

                                let user = (userBean?.data?.user)!
                                user.lastSyncNoteTime = 0

                                try! self.realm.safeWrite {

                                    self.realm.delete(userList)

                                    self.realm.add(user)
                                    userSetting?.isLogin = true
                                    
                                   //  self.tokenDelegate?.getToken(token: user.accessToken)
                                    self.token = user.accessToken
                                    print("settoken---------->")
                                    
                                    self.backToPrevious()
                                   
                                    self.performSegue(withIdentifier: "backTomain", sender: self)

                                }
                            } else {
                                self.ShowMessageAlert(msg: (userBean?.message)!)
                            }


                        } else {
                            print("error---->\(result.error.debugDescription)")
                        }
                    }
                }


            }

        }
    }
    
    

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    override func viewWillAppear(_ animated: Bool) {
        self.removeNavigationBarItem()
        self.setBackNavigationBarItem()
        self.title = "登陆"
    }

 
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
        print("prepare---------->")
        if let destination = segue.destination as? MainViewController {
            destination.token = self.token
            print("setoken---------->")
        }
        
    }
 

}
