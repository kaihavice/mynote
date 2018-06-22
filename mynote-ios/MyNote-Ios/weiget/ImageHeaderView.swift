//
//  ImageHeaderView.swift
//  MyNote-Ios
//
//  Created by xuyazhou on 2017/11/28.
//  Copyright © 2017年 xuyazhou. All rights reserved.
//

import UIKit

class ImageHeaderView: UIView {

    @IBOutlet weak var profileImage: UIImageView!

    @IBOutlet weak var backgroundImage: UIImageView!

    //var profileClick:(( ) -> ())?

    override func awakeFromNib() {
        super.awakeFromNib()
        self.backgroundColor = UIColor(hex: "E0E0E0")
        self.profileImage.layoutIfNeeded()
        self.profileImage.layer.cornerRadius = self.profileImage.bounds.size.height / 2
        self.profileImage.clipsToBounds = true
        self.profileImage.layer.borderWidth = 1
        self.profileImage.layer.borderColor = UIColor.white.cgColor
        self.backgroundImage.backgroundColor = UIColor(hex: "00c28b")
      
        //   self.profileImage.setRandomDownloadImage(80, height: 80)
        // self.backgroundImage.setRandomDownloadImage(Int(self.bounds.size.width), height: 160)

        //     let tap = UITapGestureRecognizer(target:self, action:#selector("profileClick:"))

//        let tap = UITapGestureRecognizer(target:self, action:#selector(profileClick!()))
//
//        self.profileImage.isUserInteractionEnabled=true
//        self.profileImage.addGestureRecognizer(tap)
    }
}
