//
//  UIImage.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/8/22.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import UIKit

public extension UIImage {
    public convenience init?(text: Iconfont, fontSize: CGFloat, imageSize: CGSize = CGSize.zero, imageColor: UIColor = UIColor.black) {
        guard let iconfont = UIFont.iconfont(ofSize: fontSize) else {
            self.init()
            return nil
        }
        var imageRect = CGRect(origin: CGPoint.zero, size: imageSize)
        if __CGSizeEqualToSize(imageSize, CGSize.zero) {
            imageRect = CGRect(origin: CGPoint.zero, size: text.rawValue.size(attributes: [NSFontAttributeName: iconfont]))
        }
        UIGraphicsBeginImageContextWithOptions(imageRect.size, false, UIScreen.main.scale)
        defer {
            UIGraphicsEndImageContext()
        }
        let paragraphStyle = NSMutableParagraphStyle()
        paragraphStyle.alignment = NSTextAlignment.center
        text.rawValue.draw(in: imageRect, withAttributes: [NSFontAttributeName: iconfont, NSParagraphStyleAttributeName: paragraphStyle, NSForegroundColorAttributeName: imageColor])
        guard let cgImage = UIGraphicsGetImageFromCurrentImageContext()?.cgImage else {
            self.init()
            return nil
        }
        self.init(cgImage: cgImage)
    }

//    /**
//     *  重设图片大小
//     */
//    func reSizeImage(reSize:CGSize)->UIImage {
//        //UIGraphicsBeginImageContext(reSize);
//        UIGraphicsBeginImageContextWithOptions(reSize,false,UIScreen.mainScreen().scale);
//        self.drawInRect(CGRectMake(0, 0, reSize.width, reSize.height));
//        let reSizeImage:UIImage = UIGraphicsGetImageFromCurrentImageContext();
//        UIGraphicsEndImageContext();
//        return reSizeImage;
//    }
//    
//    /**
//     *  等比率缩放
//     */
//    func scaleImage(scaleSize:CGFloat)->UIImage {
//        let reSize = CGSizeMake(self.size.width * scaleSize, self.size.height * scaleSize)
//        return reSizeImage(reSize)
//    }


}

