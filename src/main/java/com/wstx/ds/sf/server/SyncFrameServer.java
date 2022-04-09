package com.wstx.ds.sf.server;

import com.wstx.ds.sf.codec.SyncFrameDecoder;
import com.wstx.ds.config.NettyConfig;
import com.wstx.ds.sf.handler.PlbHandler;
import com.wstx.ds.sf.handler.SyncFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class SyncFrameServer implements ApplicationRunner,
        ApplicationListener<ContextClosedEvent>, ApplicationContextAware {

    @Resource
    NettyConfig config;

    //准备双循环组
    static NioEventLoopGroup bosses = new NioEventLoopGroup();
    static NioEventLoopGroup workers = new NioEventLoopGroup();
    static NioEventLoopGroup dbAccessGp = new NioEventLoopGroup();

    @Override
    public void run(ApplicationArguments args) throws Exception {

        //准备日志处理器、同步帧编解码器、同步帧处理器

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bosses, workers);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {

                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new IdleStateHandler(5,
                        0,0));
                pipeline.addLast(new LengthFieldBasedFrameDecoder(
                        1024, 12,
                        4, 0, 0));
                pipeline.addLast(new SyncFrameDecoder());
                pipeline.addLast(new SyncFrameHandler());
                pipeline.addLast(dbAccessGp,new PlbHandler());
            }
        });
        bootstrap.bind(config.getSfPort());
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent closedEvent) {
        bosses.shutdownGracefully();
        workers.shutdownGracefully();
        dbAccessGp.shutdownGracefully();
        log.debug("同步帧接收服务器停止");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    }
}
