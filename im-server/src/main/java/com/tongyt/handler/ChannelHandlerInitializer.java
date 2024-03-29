package com.tongyt.handler;

import com.tongyt.protocol.MessageCodecSharable;
import com.tongyt.protocol.ProtocolFrameDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tongyt
 * @date 2023-08-06
 */
@Component
public class ChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private MessageCodecSharable messageCodecSharable;

    @Autowired
    private LoggingHandler loggingHandler;

    @Autowired
    private LoginRequestMessageHandler loginRequestMessageHandler;

    @Autowired
    private ChatRequestMessageHandler chatRequestMessageHandler;

    @Autowired
    private GroupCreateRequestMessageHandler groupCreateRequestMessageHandler;

    @Autowired
    private GroupChatRequestMessageHandler groupChatRequestMessageHandler;

    @Autowired
    private GroupMembersRequestMessageHandler groupMembersRequestMessageHandler;

    @Autowired
    private GroupJoinRequestMessageHandler groupJoinRequestMessageHandler;

    @Autowired
    private GroupQuitRequestMessageHandler groupQuitRequestMessageHandler;

    @Autowired
    private HeartbeatHandler heartbeatHandler;

    @Autowired
    private QuitHandler quitHandler;

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new ProtocolFrameDecoder());
        ch.pipeline().addLast(loggingHandler);
        ch.pipeline().addLast(messageCodecSharable);
        // 防客户端假死，5s 内如果没有接收到客户端的消息，会触发
        //ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
        ch.pipeline().addLast(heartbeatHandler);
        ch.pipeline().addLast(loginRequestMessageHandler);
        ch.pipeline().addLast(chatRequestMessageHandler);
        ch.pipeline().addLast(groupCreateRequestMessageHandler);
        ch.pipeline().addLast(groupChatRequestMessageHandler);
        ch.pipeline().addLast(groupMembersRequestMessageHandler);
        ch.pipeline().addLast(groupJoinRequestMessageHandler);
        ch.pipeline().addLast(groupQuitRequestMessageHandler);
        ch.pipeline().addLast(quitHandler);
    }
}
