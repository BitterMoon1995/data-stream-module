package com.wstx.ds.sf.codec;

import com.alibaba.fastjson.JSON;
import com.wstx.ds.common.utils.Converter;
import com.wstx.ds.sf.msg.LoginRequestMessage;
import com.wstx.ds.sf.msg.Message;
import com.wstx.ds.sf.msg.SyncFrame;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class SyncFrameCodec extends MessageToMessageCodec<ByteBuf, SyncFrame> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SyncFrame msg, List<Object> out) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

    }

    public void iEncode(ChannelHandlerContext ctx, SyncFrame msg, List<Object> out) throws Exception {
        this.encode(ctx,msg,out);
    }

    public ByteBuf iEncode(SyncFrame msg){
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        //4个字节的魔数
        buf.writeBytes(new byte[]{'w','s','t','x'});
        //2个字节的版本号
        buf.writeShort(64);
        //1个字节的序列化方式 protobuf-0 json-1 jdk-2
        buf.writeByte(2);
        //1个字节的指令类型
        buf.writeByte(msg.getMsgType());
        //4个字节的请求序号（双工通信要用）
        buf.writeInt(1010);

        //json序列化为字符数组
        byte[] jsonBytes = JSON.toJSONBytes(msg);
        int length = jsonBytes.length;

        //填入4个字节的长度

        buf.writeInt(length);

        //最后写入内容
        buf.writeBytes(jsonBytes);

        return buf;
    }
}
