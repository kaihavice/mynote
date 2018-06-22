//
//  UIView.swift
//  SlideMenuControllerSwift
//
//  Created by xuyazhou on 2017/11/28.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit

extension UIView {
    class func loadNib<T: UIView>(_ viewType: T.Type) -> T {
        let className = String.className(viewType)
        return Bundle(for: viewType).loadNibNamed(className, owner: nil, options: nil)!.first as! T
    }

    class func loadNib() -> Self {
        return loadNib(self)
    }
}
