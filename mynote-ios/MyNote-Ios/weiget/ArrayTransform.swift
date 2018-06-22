//
//  ArrayTransform.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2018/1/2.
//  Copyright © 2018年 xuyazhou. All rights reserved.
//

import Foundation

import UIKit
import RealmSwift
import ObjectMapper

class ArrayTransform<T: RealmSwift.Object>: TransformType where T: Mappable {
    typealias Object = List<T>
    typealias JSON = Array<AnyObject>

    func transformFromJSON(_ value: Any?) -> List<T>? {
        let realmList = List<T>()

        if let jsonArray = value as? Array<Any> {
            for item in jsonArray {
                if let realmModel = Mapper<T>().map(JSONObject: item) {
                    realmList.append(realmModel)
                }
            }
        }

        return realmList
    }

//    func transformToJSON(_ value: List<T>?) -> Array<AnyObject>? {
//
//        guard let realmList = value, realmList.count > 0 else {
//            return nil
//        }
//
//        var resultArray = Array<T>()
//
//        for entry in realmList {
//            resultArray.append(entry)
//        }
//
//        return resultArray
//    }
    
    
    func transformToJSON(_ value: List<T>?) -> Array<AnyObject>? {
        if (value?.count)! > Int(0) {
            var result = Array<AnyObject>()
            for entry in value! {
                result.append(Mapper().toJSON(entry) as AnyObject)
            }
            return result
        }
        return nil
    }
}
