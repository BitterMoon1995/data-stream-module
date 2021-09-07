package com.wstx.ds.sf.codec;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ChannelHandler.Sharable
public class SyncFrameCodec extends MessageToMessageCodec {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {

    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object o, List list) throws Exception {

    }
}
