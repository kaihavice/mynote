//
//  StringUtils.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/13.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation

public class StringUtils {

    class func randomString(length: Int) -> String {
        let charSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        var c = charSet.map {
            String($0)
        }
        var s: String = ""
        for _ in (1...length) {
            s.append(c[Int(arc4random()) % c.count])
        }
        return s
    }
}
