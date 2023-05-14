package com.tongyt.protocol;


import com.tongyt.config.Config;
import com.tongyt.message.LoginRequestMessage;
import com.tongyt.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author tongyt
 * @date 2023-05-08
 */
public class SerializerTest {

    public static void main(String[] args) {

        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);

        EmbeddedChannel channel = new EmbeddedChannel(LOGGING_HANDLER, MESSAGE_CODEC, LOGGING_HANDLER);

        LoginRequestMessage message = new LoginRequestMessage("zhangsan", "123");

        // channel.writeOutbound(message);
        ByteBuf buf = messageToByteBuf(message);
        channel.writeInbound(buf);


    }

    public static ByteBuf messageToByteBuf(Message msg) {
        ByteBuf out = ByteBufAllocator.DEFAULT.buffer();
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
        return out;
    }

}
