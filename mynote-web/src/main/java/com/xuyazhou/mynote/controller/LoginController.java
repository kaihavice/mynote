package com.xuyazhou.mynote.controller;


import com.xuyazhou.mynote.model.bean.MemberTokenType;
import com.xuyazhou.mynote.model.bean.UserbeanBack;
import com.xuyazhou.mynote.service.IMemberLoginRegistService;
import com.xuyazhou.mynote.service.IMemberService;
import com.xuyazhou.mynote.web.ResponseEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * <p>
 * Date: 2016-12-09
 */
@Controller
@RequestMapping("/mcenter")
public class LoginController {

    @Autowired
    private IMemberLoginRegistService memberLoginService;

    @Autowired
    private IMemberService memberService;

    @RequestMapping(value = "/link", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity link() throws Exception {
        Map<String, String> m_result = memberLoginService.getNonce();
        return ResponseEntity.getEntity(m_result);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(HttpServletRequest request, @RequestParam String email,
                                @RequestParam String password, @RequestParam(required = false) String deviceType) throws Exception {

        String randomToken = request.getHeader("randomToken");


        UserbeanBack userbeanBack = memberLoginService.login(randomToken, email, password);


        // 保存移动端token
        if (StringUtils.isNotEmpty(deviceType)) {
            memberService.saveOrUpdateMemberToken(userbeanBack.getUser().getSid(), deviceType,
                    MemberTokenType.getMemberTokenTypeByValue(deviceType));
        }


        return ResponseEntity.getEntity(userbeanBack);

    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(HttpServletRequest request) throws Exception {

        String randomToken = request.getHeader("accessToken");

        memberLoginService.logout(randomToken);

        return ResponseEntity.getEntity("");

    }

}
