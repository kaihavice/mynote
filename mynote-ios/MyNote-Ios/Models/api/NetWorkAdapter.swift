//
//  NetWorkAdapter.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/7.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import Moya

struct NewWork {

////    let endpointClouse = { (target:NoteApi ) -> Endpoint<NoteApi> in
////        let defaultEndpoint = MoyaProvider.defaultEndpointMapping(for: target)
////         let argument = HttpArgument<NoteApi>()
////        switch target {
////        case .link:
////        return defaultEndpoint
////        case .login:
////            return defaultEndpoint.
////        default:
////             return defaultEndpoint
////        }
////
//
//
//    }

//    static let provider = MoyaProvider<NoteApi>(plugins: [NetworkLoggerPlugin(verbose: true)])
//
//
//    static func request(
//            target: NoteApi,
//            success successCallback: @escaping (JSON) -> Void,
//            error errorCallback: @escaping (Int) -> Void,
//            failure failureCallback: @escaping (MoyaError) -> Void
//    ) {
//        provider.request(target) { result in
//            switch result {
//            case let .success(response):
//                do {
//                    //如果数据返回成功则直接将结果转为JSON
//                    try response.filterSuccessfulStatusCodes()
//                    let json = try JSON(response.mapJSON())
//                    successCallback(json)
//                } catch let error {
//                    //如果数据获取失败，则返回错误状态码
//                    errorCallback((error as! MoyaError).response!.statusCode)
//                }
//            case let .failure(error):
//                //如果连接异常，则返沪错误信息（必要时还可以将尝试重新发起请求）
//                //if target.shouldRetry {
//                //    retryWhenReachable(target, successCallback, errorCallback,
//                //      failureCallback)
//                //}
//                //else {
//                failureCallback(error)
//                    //}
//            }
//        }
//    }

}
