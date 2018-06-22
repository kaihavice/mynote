//
//  AuthPlugin.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/7.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import Moya

struct AuthPlugin: PluginType {
    let token: String?

    func prepare(_ request: URLRequest, target: TargetType) -> URLRequest {
        var request = request


        request.addValue(token!, forHTTPHeaderField: "accessToken")

        return request

    }

    func willSend(_ request: RequestType, target: TargetType) {

    }


}
