package com.free.agent.controller;

import com.free.agent.Response;
import com.free.agent.model.Message;
import com.free.agent.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * Created by antonPC on 31.07.15.
 */
@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/message/unread", method = RequestMethod.GET)
    public
    @ResponseBody
    String getq(Principal principal) {
        return Response.ok(messageService.countUnreadMessages(principal.getName()));
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public
    @ResponseBody
    String get(Principal principal) {
        return Response.ok(messageService.findAllByReceiver(principal.getName()));
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public
    @ResponseBody
    String get(Long idReceiver, Message message, String email, Principal principal) {
        messageService.save(idReceiver, message, principal == null ? email : principal.getName());
        return Response.ok();
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.POST)
    public
    @ResponseBody
    String get(@PathVariable("id") Long id, Principal principal) {
        messageService.updateMessageStatus(id, principal.getName());
        return Response.ok();
    }

}
