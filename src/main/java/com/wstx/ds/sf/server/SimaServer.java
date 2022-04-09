package com.wstx.ds.sf.server;

import com.wstx.ds.sf.codec.SyncFrameDecoder;
import com.wstx.ds.sf.handler.PlbHandler;
import com.wstx.ds.sf.handler.SyncFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

public class SimaServer {

//    public static void main(String[] args) {
//
//        NioEventLoopGroup bosses = new NioEventLoopGroup();
//        NioEventLoopGroup workers = new NioEventLoopGroup();
//        NioEventLoopGroup dbAccessGp = new NioEventLoopGroup();
//
//        //准备日志处理器、同步帧编解码器、同步帧处理器
//        SyncFrameDecoder syncFrameDecoder = new SyncFrameDecoder();
//        SyncFrameHandler syncFrameHandler = new SyncFrameHandler();
//        PlbHandler plbHandler = new PlbHandler();
//
//        ServerBootstrap bootstrap = new ServerBootstrap();
//        bootstrap.group(bosses, workers);
//        bootstrap.channel(NioServerSocketChannel.class);
//        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
//            @Override
//            protected void initChannel(NioSocketChannel ch) throws Exception {
//
//                ch.pipeline().addLast(
//                        new LoggingHandler()
////                        frame length没有正确读到，头+长度字段总共18位，多了两位，怎么回事？
////                        new LengthFieldBasedFrameDecoder(
////                                1024, 12,
////                                4, 0, 0),
////                        syncFrameDecoder,
////                        syncFrameHandler
//                );
//            }
//        });
//        bootstrap.bind(9966);
//    }
}
