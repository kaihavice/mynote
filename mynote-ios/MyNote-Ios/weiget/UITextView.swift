//
//  UITextView.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/10/12.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import UIKit

extension UITextView {

    func autoHeight() {
        let contentSize = self.sizeThatFits(self.bounds.size)
        self.frame.size.height = contentSize.height
    }


    func setPaddingAndText(padding: CGFloat, text: String, textSize: CGFloat) {

        let paraph = NSMutableParagraphStyle()

        paraph.lineSpacing = padding

        let attributes = [NSFontAttributeName: UIFont.systemFont(ofSize: textSize),
                          NSParagraphStyleAttributeName: paraph]

        self.attributedText = NSAttributedString(string: text, attributes: attributes)
    }


}
