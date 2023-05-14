package com.tongyt.protocol;


import com.tongyt.config.Config;
import com.tongyt.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Message 编解码器 Sharable
 * <p>
 * 自定义协议要素：
 * 魔数：用来在第一时间判定接收的数据是否为无效数据包
 * 版本号：可以支持协议的升级
 * 序列化算法：消息正文到底采用哪种序列化反序列化方式
 * 如：json、protobuf、hessian、jdk
 * 指令类型：是登录、注册、单聊、群聊… 跟业务相关
 * 请求序号：为了双工通信，提供异步能力
 * 正文长度
 * 消息正文
 * <p>
 * 自定义Message 协议：
 * 1、魔数： 4个字节
 * 2、版本号： 1个字节
 * 3、序列化算法： 1个字节 jdk 0; json 1
 * 4、指令类型： 1个字节
 * 5、请求序列： 4个字节
 * 6、对其填充，无意义： 1个字节
 * 7、正文长度： 4个字节
 * 8、消息正文： 正文长度个字节
 *
 * @author tongyt
 * @date 2023-05-05
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodecSharable extends MessageToMessageCodec<ByteBuf, Message> {

    /**
     *
     * 编码器，Message 消息根据自定义的协议编码为ByteBuf
     *
     * @param ctx ChannelHandler 上下文
     * @param msg 需要解码的Message
     * @param outList 目标ByteBuf List
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> outList) throws Exception {
        ByteBuf out = ctx.alloc().buffer();
        // 1、魔数： 4个字节 IMMP(IM Message Protocol)
        out.writeBytes(new byte[]{'I', 'M', 'M', 'P'});
        // 2、版本号： 1个字节 版本 1
        out.writeByte(1);
        // 3、序列化算法： 1个字节 jdk 0; json 1
        out.writeByte(Config.getSerializerAlgorithm().getType());
        // 4、指令类型： 1个字节 跟业务相关
        out.writeByte(msg.getMessageType());
        // 5、请求序列： 4个字节
        out.writeInt(msg.getSequenceId());
        // 6、对其填充，无意义： 1个字节
        out.writeByte(0xff);
        // 7、正文长度： 4个字节
        byte[] bytes = Config.getSerializerAlgorithm().serialize(msg);
        out.writeInt(bytes.length);
        // 8、消息正文： 正文长度个字节
        out.writeBytes(bytes);
        // log.debug("length:{}", bytes.length);
        outList.add(out);
    }

    /**
     * 解码器，ByteBuf 根据自定义的协议解码为Message
     *
     * @param ctx ChannelHandler 上下文
     * @param in  需要解码的ByteBuf
     * @param out 目标Message
     * @throws Exception
     *
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 1、魔数： 4个字节
        int magicNum = in.readInt();
        // 2、版本号： 1个字节
        byte version = in.readByte();
        // 3、序列化算法： 1个字节 jdk 0; json 1
        byte serializerType = in.readByte();
        // 4、指令类型： 1个字节 跟业务相关
        byte messageType = in.readByte();
        // 5、请求序列： 4个字节
        int sequenceId = in.readInt();
        // 6、对其填充，无意义： 1个字节
        in.readByte();
        // 7、正文长度： 4个字节
        int length = in.readInt();
        // 8、消息正文： 正文长度个字节
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        Serializer.Algorithm algorithm = Serializer.Algorithm.getAlgorithm(serializerType);
        Object object = algorithm.deserialize(Message.getMessageClass(messageType), bytes);
        // log.debug("magicNum:{},version:{},serializerType:{},messageType:{},sequenceId:{},length:{}", magicNum, version, serializerType, messageType, sequenceId, length);
        // log.debug("message: {}", message);
        out.add(object);
    }
}
