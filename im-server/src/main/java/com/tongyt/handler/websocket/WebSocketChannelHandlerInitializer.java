package com.tongyt.handler.websocket;

import com.tongyt.handler.*;
import com.tongyt.protocol.MessageCodecSharable;
import com.tongyt.protocol.ProtocolFrameDecoder;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author tongyt
 * @date 2023-08-06
 */
@Component
public class WebSocketChannelHandlerInitializer extends ChannelInitializer<SocketChannel> {

    @Autowired
    private WebSocketMessageCodecSharable webSocketMessageCodecSharable;

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
        ch.pipeline().addLast(loggingHandler);
        //因为基于http协议，使用http的编码和解码器
        ch.pipeline().addLast(new HttpServerCodec());
        //是以块方式写，添加ChunkedWriteHandler处理器
        //ch.pipeline().addLast(new ChunkedWriteHandler());
        /*
          说明
          1. http数据在传输过程中是分段, HttpObjectAggregator ，就是可以将多个段聚合
          2. 这就就是为什么，当浏览器发送大量数据时，就会发出多次http请求
        */
        ch.pipeline().addLast(new HttpObjectAggregator(65536));
        /* 说明
          1. 对应websocket ，它的数据是以 帧(frame) 形式传递
          2. 可以看到WebSocketFrame 下面有六个子类
          3. 浏览器请求时 ws://localhost:10000/websocket 表示请求的uri
          4. WebSocketServerProtocolHandler 核心功能是将 http协议升级为 ws协议 , 保持长连接
          5. 是通过一个 状态码 101
        */
        ch.pipeline().addLast(new WebSocketServerProtocolHandler("/"));

        ch.pipeline().addLast(webSocketMessageCodecSharable);

        ch.pipeline().addLast(loginRequestMessageHandler);
        // 防客户端假死，5s 内如果没有接收到客户端的消息，会触发
        //ch.pipeline().addLast(new IdleStateHandler(5, 0, 0));
        ch.pipeline().addLast(heartbeatHandler);
        ch.pipeline().addLast(chatRequestMessageHandler);
        ch.pipeline().addLast(groupCreateRequestMessageHandler);
        ch.pipeline().addLast(groupChatRequestMessageHandler);
        ch.pipeline().addLast(groupMembersRequestMessageHandler);
        ch.pipeline().addLast(groupJoinRequestMessageHandler);
        ch.pipeline().addLast(groupQuitRequestMessageHandler);
        ch.pipeline().addLast(quitHandler);
    }
}
