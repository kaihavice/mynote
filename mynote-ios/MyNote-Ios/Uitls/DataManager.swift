//
//  DataManager.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/13.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import UIKit
import RealmSwift

public class DataManger {

    static let realm = try! Realm()

    static func creatUUid() -> String {
        let uuid = UUID().uuidString


        let newUUID = uuid.pregReplace(pattern: "[^a-zA-Z0-9]", with: "")
        print(newUUID)

        let uid = StringUtils.randomString(length: 1) + (newUUID.base64urlToBase64()) + StringUtils.randomString(length: 1)

        print(uid.lowercased())

        return uid.lowercased()
    }


    static func addCanUpdate<T: Object>(_ object: T) {
        try! realm.write {
            realm.add(object, update: true)
        }
    }

    static func add<T: Object>(_ object: T) {
        try! realm.write {
            realm.add(object)
        }
    }


    static func delete<T: Object>(_ object: T) {
        try! realm.write {
            realm.delete(object)
        }
    }

    static func delete<T: Object>(_ objects: [T]) {
        try! realm.write {
            realm.delete(objects)
        }
    }

    static func delete<T: Object>(_ objects: List<T>) {
        try! realm.write {
            realm.delete(objects)
        }
    }

    static func delete<T: Object>(_ objects: Results<T>) {
        try! realm.write {
            realm.delete(objects)
        }
    }


    static func deleteAll() {
        try! realm.write {
            realm.deleteAll()
        }
    }

    static func selectByNSPredicate<T: Object>(_: T.Type, predicate: NSPredicate) -> Results<T> {
        return realm.objects(T.self).filter(predicate)
    }

    static func selectByAll<T: Object>(_: T.Type) -> Results<T> {
        return realm.objects(T.self)
    }

    static func selectItem<T: Object>(_: T.Type) -> T {
        return realm.objects(T.self).first!
    }

    static func getRealm() -> Realm {
        return realm
    }


    static func doWriteHandler(_ clouse: @escaping () -> ()) { // 这里用到了 Trailing 闭包
        try! realm.write {
            clouse()
        }
    }

    static func BGDoWriteHandler(_ clouse: @escaping () -> ()) {
        try! Realm().write {
            clouse()
        }
    }


}
