package com.free.agent.controller;

import com.free.agent.Response;
import com.free.agent.model.Message;
import com.free.agent.service.MessageService;
import com.free.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by antonPC on 31.07.15.
 */
@Controller
public class MessageController {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    //TODO
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public
    @ResponseBody
    String get(Message message) {
        return Response.ok("OK");
    }

}
