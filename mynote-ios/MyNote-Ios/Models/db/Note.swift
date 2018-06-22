//
//  Note.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/8.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import RealmSwift
import ObjectMapper

class Note: Object, Mappable {

    dynamic var userId = ""
    dynamic var noteId = ""
    dynamic var kind = ""
    dynamic var content = ""
    dynamic var contentOld = ""
    dynamic var isAttach = false
    dynamic var createTime = 0
    dynamic var modifiedTime = 0
    dynamic var status = 0
    dynamic var deleted = false
    var requestAttachment: RequestAttachment?
    var requestCheck: RequestCheck?

    var attachmentList = List<AttachMent>()
    var checkListItemList = List<CheckListItem>()


    override static func primaryKey() -> String? {
        return "noteId"
    }

    required convenience init?(map: Map) {
        self.init()
    }

    func mapping(map: Map) {

        if map.mappingType == .toJSON {
            var noteId = self.noteId
            noteId <- map["noteId"]
        } else {
            noteId <- map["noteId"]
        }
//        if noteId == nil {
//             noteId <- map["noteId"]
//        }

        userId <- map["userId"]
        kind <- map["kind"]
        content <- map["content"]
        createTime <- (map["createTime"])
        modifiedTime <- (map["modifiedTime"])
        isAttach <- map["isAttach"]
        contentOld <- map["contentOld"]
        deleted <- map["deleted"]
        requestAttachment <- map["requestAttachment"]
        requestCheck <- map["requestCheck"]
        attachmentList <- (map["attachmentList"], ArrayTransform<AttachMent>())
        checkListItemList <- (map["checkListItemList"], ArrayTransform<CheckListItem>())

    }


}


