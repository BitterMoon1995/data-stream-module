package com.wstx.ds.sf.client;

import com.alibaba.fastjson.JSON;
import com.wstx.ds.sf.msg.SyncFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NettyTestClient {
    @Test
    @DisplayName("发JSON串测试测试")
    public void nettyClient() throws InterruptedException {

        NioEventLoopGroup workers = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(workers);
        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        byte[] bytes = ClientFunction.getEncodedBA("0X01010006");

                        //粘包、半包测试
                        for (int i = 0; i < 1; i++) {
                            ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
                            buf.writeBytes(bytes);
                            System.out.println(buf.toString(StandardCharsets.UTF_8));

                            ChannelFuture channelFuture = ctx.writeAndFlush(buf);
                            channelFuture.sync();
                        }
                    }
                },
                        //不影响结果
                        new StringDecoder(StandardCharsets.UTF_8));
            }
        });
        bootstrap.connect("127.0.0.1", 9966)
                .sync().channel().close().sync();
        workers.shutdownGracefully();
    }

    /*
    就是编码的问题，不能ByteBuf.toString.getBytes，这样读出来的byte[]有错！！！
    只能逐字节把readable的字节从ByteBuf读出来
     */
    @Test
    public void socketClient() throws IOException, InterruptedException {
        byte[] bytes = ClientFunction.getEncodedBA("0X01010006");

        Socket socket = new Socket("127.0.0.1", 9966);
        OutputStream oStream = socket.getOutputStream();
        for (int i = 0; i < 2; i++) {
            oStream.write(bytes);
            oStream.flush();
            Thread.sleep(1000);
        }
        Thread.sleep(1000 * 60 * 60 * 24);
    }

    @Test
    public void test() {
        String s = "{\"@type\":\"com.wstx.ds.sf.msg.SyncFrame\",\"altitudeGnd\":50.5,\"altitudeMsl\":490.5,\"cucsSn\":\"0x01010001\",\"datalinkId\":\"0x0101\",\"firmware\":100,\"heading\":60.5,\"latitude\":104.26587,\"longitude\":34.56485,\"missionSn\":\"0x01010001\",\"msgType\":1,\"safefailure\":\"0xFF\",\"sequenceNum\":0,\"syncCode\":\"01131723\",\"systemStatus\":\"0xFF\",\"timestamp\":1631327901229,\"vehicleSn\":\"0X01010006\",\"waypoint\":124}";
        SyncFrame syncFrame = JSON.parseObject(s, SyncFrame.class);
        System.out.println(syncFrame);
    }


}
