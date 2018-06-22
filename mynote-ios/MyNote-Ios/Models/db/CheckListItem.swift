//
//  CheckListItem.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/8.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import RealmSwift
import ObjectMapper


class CheckListItem : Object, Mappable {


    dynamic var noteId = ""
    dynamic var title = ""
    dynamic var createTime = 0
    dynamic var modifiedTime = 0
    dynamic var status = 0
    dynamic var userId = 0
    dynamic var sortOder = 0
    dynamic var deleted = false
    dynamic var checked = false
    dynamic var sid = ""

    override static func primaryKey() -> String? {
        return "sid"
    }


    required convenience init?(map: Map) {
        self.init()
    }

    func mapping(map: Map) {
        noteId <- map["noteId"]
        title <- map["title"]
        userId <- map["userId"]
        sortOder <- map["sortOder"]
        createTime <- (map["createTime"])
        modifiedTime <- (map["modifiedTime"])
        if map.mappingType == .toJSON {
            var sid = self.sid
            sid <- map["sid"]
        } else {
            sid <- map["sid"]
        }
        checked <- map["checked"]
        deleted <- map["deleted"]
        status <- map["status"]

    }
}
