package com.wstx.ds.sf.codec;

import com.alibaba.fastjson.JSON;
import com.wstx.ds.common.utils.Printer;
import com.wstx.ds.sf.msg.Message;
import com.wstx.ds.sf.msg.SyncFrame;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class SyncFrameDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        short version = in.readShort();
        byte serializerWay = in.readByte();
        byte msgType = in.readByte();
        int sequenceNum = in.readInt();
        int length = in.readInt();

        if (msgType != Message.SyncFrame){
            log.debug("非同步帧");
            return;
        }

        //准备缓冲区字节数组
        byte[] contentBytes = new byte[length];
        in.readBytes(contentBytes,0,length);

        String msgStr = new String(contentBytes, StandardCharsets.UTF_8);
        SyncFrame syncFrame =
                JSON.parseObject(msgStr, SyncFrame.class);

        out.add(syncFrame);
    }
}
