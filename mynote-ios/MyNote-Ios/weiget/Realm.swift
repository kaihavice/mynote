//
//  Realm.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/22.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import RealmSwift


extension Realm {
    public func safeWrite(_ block: (() throws -> Void)) throws {
        if isInWriteTransaction {
            try block()
        } else {
            try write(block)
        }
    }
}
