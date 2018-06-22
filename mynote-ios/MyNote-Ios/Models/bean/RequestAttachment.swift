//
//  RequestAttachment.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/20.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import ObjectMapper

class RequestAttachment: Mappable {

    required init?(map: Map) {

    }


    var add = [AttachMent]()

    var delete = [AttachMent]()


    init(add: [AttachMent], delete: [AttachMent]) {
        self.add = add

        self.delete = delete
    }


    func mapping(map: Map) {
        add <- map["add"]
        delete <- map["delete"]

    }

}
