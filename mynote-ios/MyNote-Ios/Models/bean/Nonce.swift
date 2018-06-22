//
//  Nonce.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/7.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import ObjectMapper

class Nonce: Mappable {

    var randomToken: String?
    var nonce: String?

    required init?(map: Map) {

    }

    func mapping(map: Map) {
        randomToken <- map["randomToken"]
        nonce <- map["nonce"]
    }


}
