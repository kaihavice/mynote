//
//  loginIndexViewController.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/6.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit

class loginIndexViewController: UIViewController {
    @IBOutlet weak var logoView: UIImageView!
    @IBOutlet weak var registerButton: UIButton!


    @IBOutlet weak var loginButton: UIButton!

    override func viewDidLoad() {
        super.viewDidLoad()

        registerButton.backgroundColor = UIColor(hex: "00c28b")
        registerButton.layer.cornerRadius = 5
        registerButton.setTitleColor(UIColor.white, for: .normal)
        registerButton.setTitleColor(UIColor(hex: "00c28b"), for: .highlighted)
        loginButton.layer.cornerRadius = 5
        loginButton.backgroundColor = UIColor(hex: "00c28b")
        loginButton.setTitleColor(UIColor.white, for: .normal)
        loginButton.setTitleColor(UIColor(hex: "00c28b"), for: .highlighted)
        logoView.contentMode = .scaleToFill
        logoView.contentMode = .center


        logoView.image = UIImage(text: Iconfont.app_login_logo, fontSize: 45, imageSize: CGSize(width: 43, height: 43), imageColor: UIColor.white)

        logoView.backgroundColor = UIColor(hex: "00c28b")

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    override func viewWillAppear(_ animated: Bool) {
        self.removeNavigationBarItem()
        self.setBackNavigationBarItem()
        self.title = "注册或登陆"
        
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
