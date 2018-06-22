//
//  UserSetting.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/11/29.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import RealmSwift

class UserSetting: Object {
    dynamic var id = 0
    dynamic var lockType = ""
    dynamic var createTime = 0
    dynamic var modifiedTime = 0
    dynamic var deleted = false
    dynamic var isLogin = false
    dynamic var listType = ""
    dynamic var isHaveNew = false
    dynamic var isHavedelete = false
    

    override static func primaryKey() -> String? {
        return "id"
    }
}
