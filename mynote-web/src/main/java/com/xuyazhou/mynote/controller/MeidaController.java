package com.xuyazhou.mynote.controller;

import com.xuyazhou.mynote.Utils.QiniuUtils;
import com.xuyazhou.mynote.web.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-03
 */
@Controller
@RequestMapping("/qiniu")
public class MeidaController {


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity uploadFile(HttpServletRequest request, @RequestParam String noteId, @RequestParam String fileBytes,
                                     @RequestParam String fileprefix) throws Exception {

        return ResponseEntity.getEntity(QiniuUtils.UploadByteFile(fileBytes, noteId, fileprefix));
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity deleteFile(HttpServletRequest request, @RequestParam String key) throws Exception {


        return ResponseEntity.getEntity(QiniuUtils.deteFile(key));
    }

}
