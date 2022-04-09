package com.wstx.ds.sf.handler;

import com.wstx.ds.common.utils.BeanUtils;
import com.wstx.ds.db.SyncFrameMapper;
import com.wstx.ds.sf.msg.SyncFrame;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 * persistence layer business handler，同步帧持久层业务处理器
 */
@Component
@ChannelHandler.Sharable
public class PlbHandler extends SimpleChannelInboundHandler<SyncFrame> {

    static SyncFrameMapper sfMapper = BeanUtils.getBean(SyncFrameMapper.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SyncFrame syncFrame) {
//        System.out.println("PlbHandler.channelRead0");

//        sfMapper.insert(syncFrame);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }
}
