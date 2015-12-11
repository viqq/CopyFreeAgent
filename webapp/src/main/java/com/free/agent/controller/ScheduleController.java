package com.free.agent.controller;

import com.free.agent.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Created by antonPC on 06.12.15.
 */
@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
}
