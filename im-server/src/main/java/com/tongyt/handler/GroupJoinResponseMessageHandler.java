package com.tongyt.handler;


import com.tongyt.message.GroupJoinResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class GroupJoinResponseMessageHandler extends SimpleChannelInboundHandler<GroupJoinResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinResponseMessage msg) throws Exception {
        System.out.println(msg.getReason());
    }
}
