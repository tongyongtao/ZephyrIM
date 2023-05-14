package com.tongyt.handler;

import com.tongyt.message.GroupCreateResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class GroupCreateResponseMessageHandler extends SimpleChannelInboundHandler<GroupCreateResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateResponseMessage msg) throws Exception {
        System.out.println(msg.getReason());
    }
}
