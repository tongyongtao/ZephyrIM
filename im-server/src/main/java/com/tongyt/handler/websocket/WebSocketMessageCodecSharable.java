package com.tongyt.handler.websocket;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.tongyt.message.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * WebSocketFrame格式、Message格式互转
 * @author tongyt
 * @date 2023-09-27
 */
@Slf4j
@ChannelHandler.Sharable
public class WebSocketMessageCodecSharable extends MessageToMessageCodec<TextWebSocketFrame, Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        String text = JSON.toJSONString(msg);
        out.add(new TextWebSocketFrame(text));
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
        String text = msg.text();
        Message messageType = JSON.parseObject(text, Message.class);
        Message message = JSON.parseObject(text, Message.getMessageClass(messageType.getMessageType()));
        out.add(message);
    }
}
