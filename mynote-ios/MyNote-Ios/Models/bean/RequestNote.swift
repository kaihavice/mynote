//
//  RequestNote.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/18.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import ObjectMapper

class RequestNote: Mappable {

    required init?(map: Map) {

    }


    var add = [Note]()
    var update = [Note]()
    var delete = [Note]()


    init(add: [Note], update: [Note], delete: [Note]) {
        self.add = add
        self.update = update
        self.delete = delete
    }


    func mapping(map: Map) {
        add <- map["add"]
        update <- map["update"]
        delete <- map["delete"]

    }


}
