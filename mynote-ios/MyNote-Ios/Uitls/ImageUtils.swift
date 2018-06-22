//
//  ImageUtils.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/11/1.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import UIKit

public class ImageUtils {

    class func zipImage(currentImage: UIImage, scaleSize: CGFloat, percent: CGFloat) -> NSData {
        //压缩图片尺寸

        UIGraphicsBeginImageContext(CGSize(width: currentImage.size.width * scaleSize, height: currentImage.size.height * scaleSize))
        currentImage.draw(in: CGRect(x: 0, y: 0, width: currentImage.size.width * scaleSize, height: currentImage.size.height * scaleSize))
        let newImage: UIImage = UIGraphicsGetImageFromCurrentImageContext()!
        UIGraphicsEndImageContext()
        //高保真压缩图片质量
        //UIImageJPEGRepresentation此方法可将图片压缩，但是图片质量基本不变，第二个参数即图片质量参数。
        let imageData: NSData = UIImageJPEGRepresentation(newImage, percent)! as NSData
        return imageData
    }
}
