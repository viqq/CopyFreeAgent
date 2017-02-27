package com.free.agent.job;

import com.free.agent.service.MessageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by antonPC on 30.07.15.
 */
@Service
public class CleanMessageJob {

    private static final Logger LOGGER = Logger.getLogger(CleanMessageJob.class);
    @Autowired
    private MessageService service;

    @Scheduled(cron = "0 0 12 * * ?")
    private void removeOldMessages() {
        LOGGER.info("Clean messages");
        service.removeOldMessages();
    }
}
