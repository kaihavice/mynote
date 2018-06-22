//
//  SyncStatus.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/11.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation

struct SyncStatus {
    static let LocalNew = 0
    static let Delete = -1
    static let LocalUpdate = 1
    static let HasSync = 9
}
