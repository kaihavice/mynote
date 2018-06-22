//
//  AppDelegate.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/8/22.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit
import SlideMenuControllerSwift
import CoreData
import RealmSwift

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?


    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplicationLaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.


        let storyboard = UIStoryboard(name: "Main", bundle: nil)

        let mainViewController = storyboard.instantiateViewController(withIdentifier: "MainViewController") as! MainViewController
        let leftViewController = storyboard.instantiateViewController(withIdentifier: "LeftViewController") as! LeftViewController


        let nvc: UINavigationController = UINavigationController(rootViewController: mainViewController)

//        UINavigationBar.appearance().tintColor = UIColor.blue
//       UINavigationBar.appearance().backgroundColor = UIColor(hex:"00c55c")

//        UITabBar.appearance().tintColor = UIColor.white
//        UITabBar.appearance().selectedImageTintColor = UIColor(hex:"00c55c")

        leftViewController.mainViewController = nvc

        let slideMenuController = SlideMenuController(mainViewController: nvc, leftMenuViewController: leftViewController)

        slideMenuController.automaticallyAdjustsScrollViewInsets = true
        slideMenuController.delegate = mainViewController as SlideMenuControllerDelegate
        self.window?.backgroundColor = UIColor(red: 236.0, green: 238.0, blue: 241.0, alpha: 1.0)
        self.window?.rootViewController = slideMenuController
        self.window?.makeKeyAndVisible()

        let realm = try! Realm()

        let userSetting = realm.objects(UserSetting.self).first
        let user = realm.objects(User.self).first

        if userSetting == nil {
            try! realm.safeWrite  {
                realm.add(UserSetting())
            }
        } else {
            print("当前登录   \(String(describing: userSetting?.isLogin))")
            print(realm.configuration.fileURL ?? "")
        }
        
        
        if user == nil{
           let user = User()
            user.id = 0
            try! realm.safeWrite  {
                realm.add(user)
            }
        }
        
        
        
        


        return true
    }

    func applicationWillResignActive(_ application: UIApplication) {
        // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
        // Use this method to pause ongoing tasks, disable timers, and invalidate graphics rendering callbacks. Games should use this method to pause the game.
    }

    func applicationDidEnterBackground(_ application: UIApplication) {
        // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
        // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
    }

    func applicationWillEnterForeground(_ application: UIApplication) {
        // Called as part of the transition from the background to the active state; here you can undo many of the changes made on entering the background.
    }

    func applicationDidBecomeActive(_ application: UIApplication) {
        // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    }

    func applicationWillTerminate(_ application: UIApplication) {
        // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    }


}

