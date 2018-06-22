package com.xuyazhou.mynote.controller;

import com.xuyazhou.mynote.service.INoteSyncService;
import com.xuyazhou.mynote.web.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: lampard_xu(xuyazhou18@gmail.com)
 * *
 * Date: 2017-05-03
 */
@Controller
@RequestMapping("/sync")
public class NoteController {

    @Autowired
    INoteSyncService noteSynService;

    @RequestMapping(value = "/folder/{type}")
    @ResponseBody
    public ResponseEntity NoteList(@PathVariable("type") String type) throws Exception {
        return new ResponseEntity(true, "", "", null);
    }

    @RequestMapping(value = "/note/{noteId}")
    @ResponseBody
    public ResponseEntity getNote(@PathVariable("noteId") String noteId) throws Exception {
        return new ResponseEntity(true, "", "", null);
    }


    @RequestMapping(value = "/syncnote", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity syncnote(HttpServletRequest request, @RequestBody String noteData) throws Exception {

        String checkPiont = request.getHeader("checkPiont");
        String userId = (String) request.getAttribute("userId");



        return ResponseEntity.getEntity(noteSynService.saveNoteAndBack(noteData, checkPiont, userId));
    }


    @RequestMapping(value = "/note/usernotelist")
    @ResponseBody
    public ResponseEntity getNoteNext(HttpServletRequest request, @RequestParam String pageNum,
                                      @RequestParam String pageSize) throws Exception {

        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.getEntity(noteSynService.getNoteMoreByPage
                (Integer.valueOf(pageSize), Integer.valueOf(pageNum), userId));
    }

}
