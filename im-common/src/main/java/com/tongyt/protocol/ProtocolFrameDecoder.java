package com.tongyt.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author tongyt
 * @date 2023-05-05
 */
public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProtocolFrameDecoder() {
        this(Integer.MAX_VALUE, 12, 4, 0, 0 );
    }

    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
