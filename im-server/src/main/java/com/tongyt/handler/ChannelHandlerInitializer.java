package com.tongyt.handler;

import com.tongyt.protocol.MessageCodecSharable;
import com.tongyt.protocol.ProtocolFrameDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

/**
 * @author tongyt
 * @date 2023-08-06
 */
@Component
public class ChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {

    private LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    private MessageCodecSharable MESSAGE_CODEC_SHARABLE = new MessageCodecSharable();
    private LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
    private ChatRequestMessageHandler CHAT_REQUEST_HANDLER = new ChatRequestMessageHandler();
    private GroupCreateRequestMessageHandler GROUP_CREATE_REQUEST_HANDLER = new GroupCreateRequestMessageHandler();
    private GroupChatRequestMessageHandler GROUP_CHAT_REQUEST_HANDLER = new GroupChatRequestMessageHandler();
    private GroupMembersRequestMessageHandler GROUP_MEMBERS_REQUEST_HANDLER = new GroupMembersRequestMessageHandler();
    private GroupJoinRequestMessageHandler GROUP_JOIN_REQUEST_HANDLER = new GroupJoinRequestMessageHandler();
    private GroupQuitRequestMessageHandler GROUP_QUIT_REQUEST_HANDLER = new GroupQuitRequestMessageHandler();
    private HeartbeatHandler HEARTBEAT_HANDLER = new HeartbeatHandler();
    private QuitHandler QUIT_HANDLER = new QuitHandler();

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new ProtocolFrameDecoder());
        //ch.pipeline().addLast(LOGGING_HANDLER);
        ch.pipeline().addLast(MESSAGE_CODEC_SHARABLE);
        // 防客户端假死，5s 内如果没有接收到客户端的消息，会触发
        ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
        ch.pipeline().addLast(HEARTBEAT_HANDLER);
        ch.pipeline().addLast(LOGIN_HANDLER);
        ch.pipeline().addLast(CHAT_REQUEST_HANDLER);
        ch.pipeline().addLast(GROUP_CREATE_REQUEST_HANDLER);
        ch.pipeline().addLast(GROUP_CHAT_REQUEST_HANDLER);
        ch.pipeline().addLast(GROUP_MEMBERS_REQUEST_HANDLER);
        ch.pipeline().addLast(GROUP_JOIN_REQUEST_HANDLER);
        ch.pipeline().addLast(GROUP_QUIT_REQUEST_HANDLER);
        ch.pipeline().addLast(QUIT_HANDLER);
    }
}
