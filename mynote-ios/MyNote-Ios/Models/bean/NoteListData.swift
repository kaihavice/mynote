//
//  NoteListData.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/27.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import ObjectMapper

class NoteListData: Mappable {

    var noteList: [Note]?
    var ishaveNext: Bool?


    required init?(map: Map) {

    }

    func mapping(map: Map) {
        noteList <- map["noteList"]
        ishaveNext <- map["ishaveNext"]
    }


}
