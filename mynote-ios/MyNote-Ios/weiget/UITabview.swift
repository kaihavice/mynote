//
//  UITabview.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/10/16.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import Foundation
import UIKit

extension UITableView {

    func autoHeight() {
        let contentSize = self.sizeThatFits(self.bounds.size)
        self.frame.size.height = contentSize.height
    }
}
