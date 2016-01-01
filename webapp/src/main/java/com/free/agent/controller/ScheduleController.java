package com.free.agent.controller;

import com.free.agent.Response;
import com.free.agent.dto.ScheduleDto;
import com.free.agent.service.ScheduleService;
import com.free.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

import static com.free.agent.FreeAgentAPI.*;


/**
 * Created by antonPC on 06.12.15.
 */
@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = SAVE_SCHEDULE, method = RequestMethod.POST)
    @ResponseBody
    public String saveSchedule(Principal principal, ScheduleDto dto) {
        scheduleService.save(principal.getName(), dto);
        return Response.ok();
    }

    @RequestMapping(value = GET_ALL_SCHEDULE, method = RequestMethod.GET)
    @ResponseBody
    public String getAllScheduleForUser(@PathVariable("id") Long id) {
        return Response.ok(scheduleService.findAllByUserId(id));
    }

    @RequestMapping(value = EDIT_SCHEDULE, method = RequestMethod.POST)
    @ResponseBody
    public String editSchedule(@PathVariable("id") Long id, Principal principal, ScheduleDto dto) {
        if (!userService.findById(id).getEmail().equals(principal.getName())) {
            return Response.error(EDIT_PROFILE_ERROR);
        }
        scheduleService.editSchedule(id, dto);
        return Response.ok();
    }

    @RequestMapping(value = DELETE_SCHEDULE, method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteSchedule(Principal principal, @PathVariable(value = "id") Long id) {
        scheduleService.delete(principal.getName(), id);
        return Response.ok();
    }


}
