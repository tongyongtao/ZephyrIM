package com.tongyt.handler;



import com.tongyt.message.GroupCreateRequestMessage;
import com.tongyt.message.GroupCreateResponseMessage;
import com.tongyt.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        Set<String> members = msg.getMembers();
        if (GroupSessionFactory.getGroupSession().createGroup(msg.getGroupName(), members)) {
            ctx.writeAndFlush(new GroupCreateResponseMessage(true, "创建群成功"));
            List<Channel> membersChannel = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
            for (Channel channel : membersChannel) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "你已被拉入" + msg.getGroupName() + "群"));
            }
        } else {
            ctx.writeAndFlush(new GroupCreateResponseMessage(false, "你想要创建的群昵称已存在，请更换新的群昵称"));
        }
    }
}
