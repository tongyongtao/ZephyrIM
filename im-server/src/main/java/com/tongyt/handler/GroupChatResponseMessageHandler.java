package com.tongyt.handler;

import com.tongyt.message.GroupChatResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class GroupChatResponseMessageHandler extends SimpleChannelInboundHandler<GroupChatResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatResponseMessage msg) throws Exception {
        System.out.println(msg.getFrom() + ": " + msg.getContent());
    }
}
