package com.free.agent.controller;

import com.free.agent.Response;
import com.free.agent.model.Sport;
import com.free.agent.service.SportService;
import com.free.agent.utils.ExtractFunction;
import com.google.common.collect.Collections2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by antonPC on 23.06.15.
 */
@Controller
public class AdminController {

    @Autowired
    private SportService sportService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getAdminPage() {
        return "admin";
    }

    @RequestMapping(value = "/admin/sport", method = RequestMethod.GET, produces = BaseController.PRODUCES)
    @ResponseBody
    public String getAllSports() {
        return Response.ok(Collections2.transform(sportService.getAllSports(), ExtractFunction.SPORT_NAME_INVOKE));
    }

    @RequestMapping(value = "/admin/sport", method = RequestMethod.POST, produces = BaseController.PRODUCES)
    public Sport saveSport(Sport sport) {
        return sportService.save(sport);
    }

}
