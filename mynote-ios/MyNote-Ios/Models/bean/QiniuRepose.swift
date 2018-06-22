//
//  QiniuRepose.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/26.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import ObjectMapper

class QiniuRepose: Mappable {

    var key: String?
    var hash: String?
    var bucket: String?
    var fileUrl: String?
    var fsize: Int?


    required init?(map: Map) {

    }

    func mapping(map: Map) {
        key <- map["key"]
        hash <- map["hash"]
        bucket <- map["bucket"]
        fileUrl <- map["fileUrl"]
        fsize <- map["fsize"]
    }


}
