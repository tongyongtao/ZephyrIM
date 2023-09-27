package com.tongyt.server;

import com.tongyt.handler.websocket.WebSocketChannelHandlerInitializer;
import com.tongyt.handler.websocket.WebSocketMessageCodecSharable;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author tongyt
 * @date 2023-09-27
 */
@Slf4j
@Component
public class WebSocketServer {

    private ServerBootstrap serverBootstrap;

    @Autowired
    private WebSocketChannelHandlerInitializer webSocketChannelHandlerInitializer;

    /**
     * IM服务监听端口
     */
    @Value("${im.websocket.port:10000}")
    private int port;
    /**
     * 主线程组数量
     */
    @Value("${im.websocket.bossThread:1}")
    private int bossThread;

    /**
     * 启动netty服务
     */
    public void start() {
        this.init();
        serverBootstrap.bind(this.port);
        log.info("IM websocket server started on port: {} (TCP) with boss thread {}", this.port, this.bossThread);
    }

    /**
     * 初始化netty配置
     */
    public void init() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.channel(NioServerSocketChannel.class);
        this.serverBootstrap.group(boss, worker);
        this.serverBootstrap.childHandler(webSocketChannelHandlerInitializer);
    }

}
