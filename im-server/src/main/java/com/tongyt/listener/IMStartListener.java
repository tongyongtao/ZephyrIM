package com.tongyt.listener;

import com.tongyt.server.IMServer;
import com.tongyt.server.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author tongyt
 * @date 2023-08-06
 */
@Component
public class IMStartListener implements ApplicationRunner {

    @Autowired
    private IMServer imServer;

    @Autowired
    private WebSocketServer webSocketServer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        imServer.start();
        webSocketServer.start();
    }
}
