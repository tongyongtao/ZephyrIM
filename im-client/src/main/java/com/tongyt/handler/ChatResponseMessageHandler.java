package com.tongyt.handler;

import com.tongyt.message.ChatResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class ChatResponseMessageHandler extends SimpleChannelInboundHandler<ChatResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {
        if (msg.isSuccess()) {
            System.out.println(msg.getFrom()+": " + msg.getContent());
        }else {
            System.out.println(msg.getReason());
        }
    }
}
