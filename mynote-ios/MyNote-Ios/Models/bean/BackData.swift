//
//  Response.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/6.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import ObjectMapper

class BackData<T: Mappable>: Mappable {


    var data: T?
    var errCode: String?
    var message: String?
    var success: Bool?

    required init?(map: Map) {

    }

    func mapping(map: Map) {
        errCode <- map["errCode"]
        message <- map["message"]
        success <- map["success"]
        data <- map["data"]
    }
}
