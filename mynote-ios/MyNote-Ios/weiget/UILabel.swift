//
//  UILabel.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/11/2.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import UIKit

extension UILabel {

    func setPaddingAndText(padding: CGFloat, text: String, textSize: CGFloat) {

        let paraph = NSMutableParagraphStyle()

        paraph.lineSpacing = padding

        let attributes = [NSFontAttributeName: UIFont.systemFont(ofSize: textSize),
                          NSParagraphStyleAttributeName: paraph]

        self.attributedText = NSAttributedString(string: text, attributes: attributes)
    }

}

