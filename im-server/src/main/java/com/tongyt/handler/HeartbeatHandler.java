package com.tongyt.handler;

import com.tongyt.message.PingMessage;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 心跳检测处理器
 * ChannelDuplexHandler 可以同时作为入站和出战处理器
 * @author tongyt
 * @date 2023-05-07
 */
@Slf4j
@ChannelHandler.Sharable
public class HeartbeatHandler extends ChannelDuplexHandler {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            //log.info("HeartbeatHandler#userEventTriggered {}", evt);
            if (e.state() == IdleState.READER_IDLE) {
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
                ctx.writeAndFlush(new PingMessage());
            }
        }
    }
}
