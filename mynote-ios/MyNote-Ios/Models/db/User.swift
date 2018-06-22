//
//  User.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/11/29.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import RealmSwift
import ObjectMapper

class User: Object, Mappable {
    dynamic var id = 0
    dynamic var userName = ""
    dynamic var sid = ""
    dynamic var avatar = ""
    dynamic var email = ""
    dynamic var createdTime = 0
    dynamic var modifyTime = 0
    dynamic var deleted = false
    dynamic var password = ""
    dynamic var accessToken = ""
    dynamic var activity = 0
    dynamic var status = 0
    dynamic var lastSyncNoteTime = 0

    override static func primaryKey() -> String? {
        return "id"
    }

    required convenience init?(map: Map) {
        self.init()
    }
    
    
    func mapping(map: Map) {
        
        
        userName <- map["userName"]
        sid <- map["sid"]
        avatar <- map["avatar"]
        email <- map["email"]
        createdTime <- (map["createdTime"])
        modifyTime <- (map["modifyTime"])
        password <- map["password"]
        accessToken <- map["accessToken"]
        activity <- map["activity"]
        status <- map["status"]
        lastSyncNoteTime <- (map["lastSyncNoteTime"])
    }
}
