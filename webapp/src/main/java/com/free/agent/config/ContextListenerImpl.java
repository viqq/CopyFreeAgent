package com.free.agent.config;


/**
 * Created by antonPC on 06.12.15.
 */

import com.free.agent.FreeAgentAPI;
import lombok.NoArgsConstructor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@NoArgsConstructor
public class ContextListenerImpl implements ServletContextListener {

    private static final String PROD = "prod";

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String profile = PROD;
        for (String s : System.getenv().get("JAVA_OPTS").split(" ")) {
            if (s.startsWith("-Dprofile.active")) {
                profile = s.split("=")[1];
            }
        }
        servletContext.setInitParameter("spring.profiles.active", profile);
        FreeAgentAPI.host = profile.equals(PROD) ? "http://78.47.247.157:8080" : "http://localhost:8080";
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
