package com.tongyt.handler;


import com.tongyt.message.GroupJoinResponseMessage;
import com.tongyt.message.GroupQuitRequestMessage;
import com.tongyt.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class GroupQuitRequestMessageHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage msg) throws Exception {

        if (GroupSessionFactory.getGroupSession().removeMember(msg.getGroupName(), msg.getUsername()) != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, "退出" + msg.getGroupName() + "群成功"));
        }else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, "退出" + msg.getGroupName() + "群失败"));
        }
    }
}
