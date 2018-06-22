//
//  AttachMent.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/8.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import RealmSwift
import ObjectMapper

class AttachMent: Object, Mappable {


    dynamic var noteId = ""
    dynamic var fileName = ""
    dynamic var fileData: Data?
    dynamic var fileType = ""
    dynamic var spath = ""
    dynamic var sid = ""
    dynamic var createTime = 0
    dynamic var modifiedTime = 0
    dynamic var status = 0
    dynamic var size = 0
    dynamic var deleted = false

    override static func primaryKey() -> String? {
        return "sid"
    }

    required convenience init?(map: Map) {
        self.init()
    }

    func mapping(map: Map) {
        noteId <- map["noteId"]
        fileName <- map["fileName"]
        fileType <- map["fileType"]
        spath <- map["spath"]
        createTime <- (map["createTime"])
        modifiedTime <- (map["modifiedTime"])

        if map.mappingType == .toJSON {
            var sid = self.sid
            sid <- map["sid"]
        } else {
            sid <- map["sid"]
        }

        size <- map["size"]
        deleted <- map["deleted"]
        status <- map["status"]

    }
}
