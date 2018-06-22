//
//  NoteSyncPlugin.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/19.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import Moya

struct NoteSyncPlugin: PluginType {
    let checkPoint: Int!
    let token: String!

    func prepare(_ request: URLRequest, target: TargetType) -> URLRequest {
        var request = request

        request.addValue(token!, forHTTPHeaderField: "accessToken")

        request.addValue("\(checkPoint)", forHTTPHeaderField: "checkPiont")


        return request

    }

    func willSend(_ request: RequestType, target: TargetType) {

    }


}
