package com.tongyt.handler;


import com.tongyt.message.GroupChatRequestMessage;
import com.tongyt.message.GroupChatResponseMessage;
import com.tongyt.session.GroupSession;
import com.tongyt.session.GroupSessionFactory;
import com.tongyt.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Channel userChannel = SessionFactory.getSession().getChannel(msg.getFrom());

        if (!groupSession.isMember(msg.getGroupName(), msg.getFrom())) {
            ctx.writeAndFlush(new GroupChatResponseMessage(false, "你不在" + msg.getGroupName() + "群里，不可以发送群消息"));
        }

        List<Channel> membersChannel = groupSession.getMembersChannel(msg.getGroupName());

        for (Channel channel : membersChannel) {
            if (userChannel.equals(channel)) {
                continue;
            }
            channel.writeAndFlush(new GroupChatResponseMessage(msg.getFrom(), msg.getContent()));
        }
    }
}
