package com.tongyt.handler;

import com.tongyt.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@Slf4j
@ChannelHandler.Sharable
public class QuitHandler extends ChannelInboundHandlerAdapter {

    // 当连接断开时触发inactive事件
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        String username = SessionFactory.getSession().getUsername(ctx.channel());
        if (username == null) {
            return;
        }
        SessionFactory.getSession().unbind(ctx.channel());
        log.info("{} 已下线", username);
    }

    // 当异常断开时触发
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel() == null) {
            log.warn("QuitHandler channel is null, {}", ctx);
            return;
        }
        String username = SessionFactory.getSession().getUsername(ctx.channel());
        SessionFactory.getSession().unbind(ctx.channel());
        log.info("{} 已异常下线，异常：{}", username, cause);
    }
}
