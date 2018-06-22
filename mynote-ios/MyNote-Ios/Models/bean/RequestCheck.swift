//
//  RequestCheck.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/20.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import ObjectMapper

class RequestCheck: Mappable {

    required init?(map: Map) {

    }


    var add = [CheckListItem]()
    var update = [CheckListItem]()
    var delete = [CheckListItem]()


    init(add: [CheckListItem], update: [CheckListItem], delete: [CheckListItem]) {
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
