package com.tangorabox.reactiverest.util;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * https://stackoverflow.com/questions/52949088/h2-db-not-accessible-at-localhost8080-h2-console-when-using-webflux
 */
@Component
public class H2Console {

    private org.h2.tools.Server webServer;

    private org.h2.tools.Server tcpServer;

    @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        this.webServer = org.h2.tools.Server.createWebServer("-webPort", "8082", "-tcpAllowOthers").start();
        this.tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    @EventListener(org.springframework.context.event.ContextClosedEvent.class)
    public void stop() {
        this.tcpServer.stop();
        this.webServer.stop();
    }
}
