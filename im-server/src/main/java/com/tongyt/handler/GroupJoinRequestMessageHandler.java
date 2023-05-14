package com.tongyt.handler;



import com.tongyt.message.GroupJoinRequestMessage;
import com.tongyt.message.GroupJoinResponseMessage;
import com.tongyt.session.GroupSessionFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author tongyt
 * @date 2023-05-07
 */
@ChannelHandler.Sharable
public class GroupJoinRequestMessageHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage msg) throws Exception {

        if (GroupSessionFactory.getGroupSession().joinMember(msg.getGroupName(), msg.getUsername()) != null) {
            ctx.writeAndFlush(new GroupJoinResponseMessage(true, "加入" + msg.getGroupName() + "群成功"));
        }else {
            ctx.writeAndFlush(new GroupJoinResponseMessage(false, "加入" + msg.getGroupName() + "群失败"));
        }
    }
}
