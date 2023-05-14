package com.tongyt.handler;


import com.tongyt.message.GroupMembersResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.StringUtil;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class GroupMembersResponseMessageHandler extends SimpleChannelInboundHandler<GroupMembersResponseMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersResponseMessage msg) throws Exception {
        System.out.println(StringUtil.join(",", msg.getMembers()));
    }
}
