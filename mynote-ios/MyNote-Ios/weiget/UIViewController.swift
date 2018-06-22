//
//  UIViewController.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/5.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit

extension UIViewController {

    func setNavigationBarItem() {
        self.addLeftBarButtonWithImage(UIImage(named: "ic_menu_black_24dp")!)

        let rightButton = UIBarButtonItem(title: "", style: .plain, target: self,
                action: #selector(rightMenu))
        rightButton.image = UIImage(text: Iconfont.moreIcon, fontSize: 12, imageSize: CGSize(width: 10, height: 10), imageColor: UIColor.white)!
//        self.addRightBarButtonWithImage(UIImage(text: Iconfont.moreIcon, fontSize: 12, imageSize: CGSize(width: 10, height: 10), imageColor: UIColor.white)!)
        self.navigationItem.rightBarButtonItem = rightButton
        self.slideMenuController()?.removeLeftGestures()
        self.slideMenuController()?.removeRightGestures()
        self.slideMenuController()?.addLeftGestures()
        self.slideMenuController()?.addRightGestures()
    }

    func removeNavigationBarItem() {
        self.navigationItem.leftBarButtonItem = nil
        self.navigationItem.rightBarButtonItem = nil
        self.slideMenuController()?.removeLeftGestures()
        self.slideMenuController()?.removeRightGestures()
    }


    func setBackNavigationBarItem() {

        let leftBarBtn = UIBarButtonItem(title: "", style: .plain, target: self,
                action: #selector(backToPrevious))
        leftBarBtn.image = UIImage(text: Iconfont.backIcon, fontSize: 18, imageSize: CGSize(width: 14, height: 14), imageColor: UIColor.white)!

        leftBarBtn.imageInsets = UIEdgeInsetsMake(0, -15, 0, 0)

        self.navigationItem.leftBarButtonItem = leftBarBtn
//        self.addLeftBarButtonWithImage(UIImage(text: Iconfont.backIcon, fontSize: 12, imageSize: CGSize(width: 10, height: 10), imageColor: UIColor(hex: "00c28b"))!)
        self.navigationController?.navigationBar.tintColor = UIColor(hex: "00c28b")
        self.slideMenuController()?.removeLeftGestures()
        self.slideMenuController()?.removeRightGestures()
        self.slideMenuController()?.addLeftGestures()
        self.slideMenuController()?.addRightGestures()
    }

    @objc func backToPrevious() {
   

        self.navigationController!.popViewController(animated: true)

        dismiss(animated: true, completion: nil)


        print("click here")
    }

    @objc func rightMenu() {

    }
    
    func ShowMessageAlert(msg:String) {
        let alertController = UIAlertController(title: msg,
                                                message: nil, preferredStyle: .alert)
        self.present(alertController, animated: true, completion: nil)
        //两秒钟后自动消失
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now() + 2) {
            self.presentedViewController?.dismiss(animated: false, completion: nil)
        }
    }


    func getNowDataString() -> String {


        let date:Date = Date()

        let time: TimeInterval = date.timeIntervalSince1970

        return String(format: "%.0f", time)

    }


    func getNowDataInt() -> Int {


        let date:Date = Date()

        let time: TimeInterval = date.timeIntervalSince1970

        return Int(String(format: "%.0f", time))!

    }

}
