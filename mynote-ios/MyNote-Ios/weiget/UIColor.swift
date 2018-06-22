//
//  UIColor.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/9/4.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit

extension UIColor {
//    //用数值初始化颜色，便于生成设计图上标明的十六进制颜色
//    convenience init(valueRGB: UInt, alpha: CGFloat = 1.0) {
//        self.init(
//            red: CGFloat((valueRGB & 0xFF0000) >> 16) / 255.0,
//            green: CGFloat((valueRGB & 0x00FF00) >> 8) / 255.0,
//            blue: CGFloat(valueRGB & 0x0000FF) / 255.0,
//            alpha: alpha
//        )
//    }

    convenience init(hex: String) {
        self.init(hex: hex, alpha:1)
    }
    
    convenience init(hex: String, alpha: CGFloat) {
        var hexWithoutSymbol = hex
        if hexWithoutSymbol.hasPrefix("#") {
            hexWithoutSymbol = hex.substring(1)
        }
        
        let scanner = Scanner(string: hexWithoutSymbol)
        var hexInt:UInt32 = 0x0
        scanner.scanHexInt32(&hexInt)
        
        var r:UInt32!, g:UInt32!, b:UInt32!
        switch (hexWithoutSymbol.length) {
        case 3: // #RGB
            r = ((hexInt >> 4) & 0xf0 | (hexInt >> 8) & 0x0f)
            g = ((hexInt >> 0) & 0xf0 | (hexInt >> 4) & 0x0f)
            b = ((hexInt << 4) & 0xf0 | hexInt & 0x0f)
            break;
        case 6: // #RRGGBB
            r = (hexInt >> 16) & 0xff
            g = (hexInt >> 8) & 0xff
            b = hexInt & 0xff
            break;
        default:
            // TODO:ERROR
            break;
        }
        
        self.init(
            red: (CGFloat(r)/255),
            green: (CGFloat(g)/255),
            blue: (CGFloat(b)/255),
            alpha:alpha)
    }
    
}
