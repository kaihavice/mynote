//
//  UserinfoViewController.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/11/28.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit

class UserinfoViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }

    override func viewWillAppear(_ animated: Bool) {
        self.removeNavigationBarItem()
        self.setBackNavigationBarItem()
        self.title = "用户中心"
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
