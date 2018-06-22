package com.xuyazhou.mynote.controller;

import com.xuyazhou.mynote.exception.MemberException;
import com.xuyazhou.mynote.service.IMemberLoginRegistService;
import com.xuyazhou.mynote.service.IMemberService;
import com.xuyazhou.mynote.web.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-04-06
 */
@Controller
@RequestMapping("/mcenter")
public class RegisterController {

    @Autowired
    private IMemberService memberService;

    @Autowired
    private IMemberLoginRegistService memberLoginRegistService;


    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity regist(@RequestParam String email, @RequestParam String passWord) throws Exception {

        boolean isExist = memberService.existemail(email);


        if (isExist) {
            return ResponseEntity.getEntityError("账户已经被注册，请返回登录", MemberException.ACCOUT_IS_REGIST);
        } else {

            return ResponseEntity.getEntity(memberLoginRegistService.
                    regist(email, passWord));
        }
    }

}
