package com.free.agent.controller;

import com.free.agent.Response;
import com.free.agent.dto.MessageDto;
import com.free.agent.exception.EmailAlreadyUsedException;
import com.free.agent.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

import static com.free.agent.FreeAgentAPI.*;

/**
 * Created by antonPC on 31.07.15.
 */
@Controller
public class MessageController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = GET_UNREAD_MESSAGES, method = RequestMethod.GET)
    @ResponseBody
    public String getUnreadMessages(Principal principal) {
        return Response.ok(messageService.countUnreadMessages(principal.getName()));
    }

    @RequestMapping(value = GET_ALL_MESSAGES, method = RequestMethod.GET)
    @ResponseBody
    public String getAllMessages(Principal principal) {
        return Response.ok(messageService.findAllByReceiver(principal.getName()));
    }

    @RequestMapping(value = GET_SENT_MESSAGES, method = RequestMethod.GET)
    @ResponseBody
    public String getSentMessages(Principal principal) {
        return Response.ok(messageService.findAllByAuthor(principal.getName()));
    }

    @RequestMapping(value = GET_HISTORY, method = RequestMethod.GET)
    @ResponseBody
    public String getHistory(Principal principal, @PathVariable("id") Long id) {
        return Response.ok(messageService.getHistory(id, principal.getName()));
    }

    @RequestMapping(value = GET_ALL_PARTICIPANTS, method = RequestMethod.GET)
    @ResponseBody
    public String getParticipants(Principal principal) {
        return Response.ok(messageService.getParticipants(principal.getName()));
    }

    @RequestMapping(value = SAVE_MESSAGES, method = RequestMethod.POST)
    @ResponseBody
    public String saveMessage(MessageDto dto, String email, Principal principal) {
        try {
            messageService.save(dto, email, principal);
        } catch (EmailAlreadyUsedException e) {
            Response.error(EMAIL_REGISTERED_ERROR);
        }
        return Response.ok();
    }

    //todo how is it work??? is it used?
    @RequestMapping(value = GET_MESSAGE_BY_ID, method = RequestMethod.POST)
    @ResponseBody
    public String getMessageById(@PathVariable("id") Long id, Principal principal) {
        messageService.updateMessageStatus(id, principal.getName());
        return Response.ok();
    }

}
