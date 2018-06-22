//
//  UserBeanBack.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/6.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import ObjectMapper

class UserBeanBack: Mappable {

    var token: String?
    var user: User?


    required init?(map: Map) {

    }

    func mapping(map: Map) {
        token <- map["token"]
        user <- map["user"]
    }


}

