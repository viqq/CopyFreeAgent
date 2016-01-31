package com.free.agent.controller;

import com.free.agent.Response;
import com.free.agent.model.Sport;
import com.free.agent.service.SportService;
import com.free.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.free.agent.FreeAgentAPI.DELETE_USER;
import static com.free.agent.FreeAgentAPI.SAVE_SPORT;


/**
 * Created by antonPC on 23.06.15.
 */
@Controller
public class AdminController {

    @Autowired
    private SportService sportService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage() {
        return "admin";
    }

    @RequestMapping(value = DELETE_USER, method = RequestMethod.DELETE, produces = BaseController.PRODUCES)
    @ResponseBody
    public String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return Response.ok();
    }

    @RequestMapping(value = SAVE_SPORT, method = RequestMethod.POST, produces = BaseController.PRODUCES)
    public Sport saveSport(Sport sport) {
        return sportService.save(sport);
    }

}
