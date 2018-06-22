//
//  Results.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/18.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import RealmSwift

extension Results {
    func toArray<T>(ofType: T.Type) -> [T] {
        var array = [T]()
        for i in 0..<count {
            if let result = self[i] as? T {
                array.append(result)
            }
        }

        return array
    }
}
