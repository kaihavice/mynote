//
//  SyncNoteData.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/22.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import ObjectMapper

class SyncNoteData: Mappable {
    
    var checkpoint: Int?
    var syncNoteList: [Note]?
  
    
    required init?(map: Map) {
        
    }
    
    func mapping(map: Map) {
        checkpoint <- map["checkpoint"]
        syncNoteList <- map["syncNoteList"]
    }
    
    
}
