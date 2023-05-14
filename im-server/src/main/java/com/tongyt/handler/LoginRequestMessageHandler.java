package com.tongyt.handler;

import com.tongyt.message.LoginRequestMessage;
import com.tongyt.message.LoginResponseMessage;
import com.tongyt.service.UserServiceFactory;
import com.tongyt.session.SessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@Slf4j
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage msg) throws Exception {
        String username = msg.getUsername();
        String password = msg.getPassword();
        boolean login = UserServiceFactory.getUserService().login(username, password);
        LoginResponseMessage message;
        if (login) {
            SessionFactory.getSession().bind(ctx.channel(), username);
            log.info("{} 已上线", username);
            message = new LoginResponseMessage(true, "登录成功");
        } else {
            message = new LoginResponseMessage(false, "登录失败");
        }
        ctx.writeAndFlush(message);
    }
}
