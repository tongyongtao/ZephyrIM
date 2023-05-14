package com.tongyt;


import com.tongyt.handler.*;
import com.tongyt.message.*;
import com.tongyt.protocol.MessageCodecSharable;
import com.tongyt.protocol.ProtocolFrameDecoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author tongyt
 * @date 2023-05-06
 */
public class ChatClient {

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup worker = new NioEventLoopGroup();

        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC_SHARABLE = new MessageCodecSharable();
        ChatResponseMessageHandler CHAT_RESPONSE_HANDLER = new ChatResponseMessageHandler();
        GroupCreateResponseMessageHandler GROUP_CREATE_RESPONSE_HANDLER = new GroupCreateResponseMessageHandler();
        GroupChatResponseMessageHandler GROUP_CHAT_RESPONSE_HANDLER = new GroupChatResponseMessageHandler();
        GroupMembersResponseMessageHandler GROUP_MEMBERS_RESPONSE_HANDLER = new GroupMembersResponseMessageHandler();
        GroupJoinResponseMessageHandler GROUP_JOIN_RESPONSE_HANDLER = new GroupJoinResponseMessageHandler();
        GroupQuitResponseMessageHandler GROUP_QUIT_RESPONSE_HANDLER = new GroupQuitResponseMessageHandler();
        HeartbeatHandler HEARTBEAT_HANDLER = new HeartbeatHandler();

        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicBoolean isLogin = new AtomicBoolean();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.group(worker);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new ProtocolFrameDecoder());
                    //ch.pipeline().addLast(LOGGING_HANDLER);
                    ch.pipeline().addLast(MESSAGE_CODEC_SHARABLE);
                    // 防客户端假死，5s 内如果没有接收到客户端的消息，会触发
                    ch.pipeline().addLast(new IdleStateHandler(0,3,0));
                    ch.pipeline().addLast(HEARTBEAT_HANDLER);
                    ch.pipeline().addLast(CHAT_RESPONSE_HANDLER);
                    ch.pipeline().addLast(GROUP_CREATE_RESPONSE_HANDLER);
                    ch.pipeline().addLast(GROUP_CHAT_RESPONSE_HANDLER);
                    ch.pipeline().addLast(GROUP_MEMBERS_RESPONSE_HANDLER);
                    ch.pipeline().addLast(GROUP_JOIN_RESPONSE_HANDLER);
                    ch.pipeline().addLast(GROUP_QUIT_RESPONSE_HANDLER);
                    ch.pipeline().addLast("ClientHandler", new ChannelInboundHandlerAdapter() {

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            // System.out.println(msg);
                            if (msg instanceof LoginResponseMessage) {
                                LoginResponseMessage loginResponseMessage = (LoginResponseMessage) msg;
                                if (loginResponseMessage.isSuccess()) {
                                    isLogin.set(true);
                                } else {
                                    System.out.println(loginResponseMessage.getReason());
                                }
                                countDownLatch.countDown();
                            }
                        }

                        // 连接建立成功后触发active 事件
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            new Thread(new Runnable() {
                                @SneakyThrows
                                @Override
                                public void run() {
                                    Scanner scanner = new Scanner(System.in);
                                    System.out.println("请输入用户名：");
                                    String username = scanner.nextLine();
                                    System.out.println("请输入密码：");
                                    String password = scanner.nextLine();
                                    LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, password);
                                    ctx.writeAndFlush(loginRequestMessage);
                                    System.out.println("等待登录......");
                                    countDownLatch.await();

                                    if (!isLogin.get()) {
                                        ctx.channel().close();
                                        return;
                                    }
                                    while (true) {
                                        System.out.println("==================================");
                                        System.out.println("send [username] [content]");
                                        System.out.println("gsend [group name] [content]");
                                        System.out.println("gcreate [group name] [m1,m2,m3...]");
                                        System.out.println("gmembers [group name]");
                                        System.out.println("gjoin [group name]");
                                        System.out.println("gquit [group name]");
                                        System.out.println("quit");
                                        System.out.println("==================================");

                                        String command = scanner.nextLine();
                                        String[] params = command.split(" ");
                                        Message message = null;

                                        switch (params[0]) {
                                            case "send":
                                                message = new ChatRequestMessage(username, params[1], params[2]);
                                                break;
                                            case "gsend":
                                                message = new GroupChatRequestMessage(username, params[1], params[2]);
                                                break;
                                            case "gcreate":
                                                Set<String> members = new HashSet<>(Arrays.asList(params[2].split(",")));
                                                members.add(username);
                                                message = new GroupCreateRequestMessage(params[1], members);
                                                break;
                                            case "gmembers":
                                                message = new GroupMembersRequestMessage(params[1]);
                                                break;
                                            case "gjoin":
                                                message = new GroupJoinRequestMessage(username, params[1]);
                                                break;
                                            case "gquit":
                                                message = new GroupQuitRequestMessage(username, params[1]);
                                                break;
                                            case "quit":
                                                ctx.close();
                                                return;
                                            default:
                                                continue;
                                        }
                                        ctx.writeAndFlush(message);
                                    }

                                }
                            }).start();

                        }
                    });
                }
            });

            // sync 等待客户端连接上服务器后，继续往下运行
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8088).sync();

            // sync 等待客户端关闭事件后，继续往下运行
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }


    }

}
