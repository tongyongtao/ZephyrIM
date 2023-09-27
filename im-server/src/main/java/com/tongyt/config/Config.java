package com.tongyt.config;

import com.tongyt.handler.*;
import com.tongyt.handler.websocket.WebSocketMessageCodecSharable;
import com.tongyt.protocol.MessageCodecSharable;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tongyt
 * @date 2023-09-27
 */
@Configuration
public class Config {

    @Bean
    public MessageCodecSharable messageCodecSharable() {
        return new MessageCodecSharable();
    }

    @Bean
    public WebSocketMessageCodecSharable webSocketMessageCodecSharable() {
        return new WebSocketMessageCodecSharable();
    }

    @Bean
    public LoggingHandler loggingHandler(){
        return new LoggingHandler(LogLevel.DEBUG);
    }

    @Bean
    public LoginRequestMessageHandler loginRequestMessageHandler() {
        return new LoginRequestMessageHandler();
    }

    @Bean
    public ChatRequestMessageHandler chatRequestMessageHandler() {
        return new ChatRequestMessageHandler();
    }

    @Bean
    public GroupCreateRequestMessageHandler groupCreateRequestMessageHandler() {
        return new GroupCreateRequestMessageHandler();
    }

    @Bean
    public GroupChatRequestMessageHandler groupChatRequestMessageHandler() {
        return new GroupChatRequestMessageHandler();
    }

    @Bean
    public GroupMembersRequestMessageHandler groupMembersRequestMessageHandler() {
        return new GroupMembersRequestMessageHandler();
    }

    @Bean
    public GroupJoinRequestMessageHandler groupJoinRequestMessageHandler() {
        return new GroupJoinRequestMessageHandler();
    }

    @Bean
    public GroupQuitRequestMessageHandler groupQuitRequestMessageHandler() {
        return new GroupQuitRequestMessageHandler();
    }

    @Bean
    public HeartbeatHandler heartbeatHandler() {
        return new HeartbeatHandler();
    }

    @Bean
    public QuitHandler quitHandler() {
        return new QuitHandler();
    }


}
