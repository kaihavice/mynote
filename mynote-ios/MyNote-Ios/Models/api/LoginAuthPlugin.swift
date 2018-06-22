//
//  loginAuthPlugin.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/7.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation

import Moya

struct LoginAuthPlugin: PluginType {
    let randomToken: String?

    func prepare(_ request: URLRequest, target: TargetType) -> URLRequest {
        var request = request

        request.addValue(randomToken!, forHTTPHeaderField: "randomToken")

        return request

    }
}
