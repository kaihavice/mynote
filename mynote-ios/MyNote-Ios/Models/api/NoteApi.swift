//
//  NoteApi.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/12/6.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import Moya


public enum NoteApi {
    case register(String, String)
    case link
    case logout
    case login(String, String)
    case upload(String ,String ,String)
    case syncnote([String: Any]!)
    case usernotelist(Int)
}

extension NoteApi: TargetType {
    public var baseURL: URL {
        return URL(string: "http://ss.xuyazhou.com:8080/mynote/")!
    }


    public var path: String {
        switch self {
        case .register(_, _):
            return "mcenter/regist"
        case .link:
            return "mcenter/link"
        case .logout:
            return "mcenter/logout"
        case .upload(_,_,_):
            return "qiniu/upload"
        case .login(_, _):
            return "mcenter/login"
        case .syncnote(_):
            return "sync/syncnote"
        case .usernotelist(_):
            return "sync/note/usernotelist"
        }
    }

    public var method: Moya.Method {
        return .post
    }

    public var sampleData: Data {
        return "{}".data(using: String.Encoding.utf8)!
    }


    public var task: Task {
        switch self {
        case .register(let email, let password):
            var params: [String: Any] = [:]
            params["email"] = email
            params["passWord"] = password
            return .requestParameters(parameters: params,
                    encoding: URLEncoding.default)
        case .login(let email, let password):
            var params: [String: Any] = [:]
            params["email"] = email
            params["deviceType"] = 0
            params["password"] = password
            return .requestParameters(parameters: params,
                    encoding: URLEncoding.default)
        case .syncnote(let data):
            return .requestParameters(parameters: data!,
                    encoding: JSONEncoding.default)
        case .upload(let fileBytes,let fileprefix, let noteId):
             var params: [String: Any] = [:]
             params["fileBytes"] = fileBytes
              params["fileprefix"] = fileprefix
              params["noteId"] = noteId
             return .requestParameters(parameters: params,
                                       encoding: URLEncoding.default)
        case .usernotelist(let pageNum):
            var params: [String: Any] = [:]
            params["pageSize"] = 10
            params["pageNum"] = pageNum
            return .requestParameters(parameters: params,
                                      encoding: URLEncoding.default)
            
        default:
            return .requestPlain
        }
    }


    public var validate: Bool {
        return false
    }

    public var headers: [String: String]? {


        return nil


    }


}
