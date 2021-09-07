package com.wstx.ds.sf.server;

import com.wstx.ds.sf.codec.SyncFrameCodec;
import com.wstx.ds.sf.config.NettyConfig;
import com.wstx.ds.sf.handler.SyncFrameHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
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

    private ApplicationContext applicationContext;

    int port = config.getSfPort();

    //准备双循环组
    static NioEventLoopGroup bosses = new NioEventLoopGroup();
    static NioEventLoopGroup workers = new NioEventLoopGroup();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("run了");

        //准备日志处理器、同步帧编解码器、同步帧处理器
        final LoggingHandler loggingHandler = new LoggingHandler();
        SyncFrameCodec syncFrameCodec = new SyncFrameCodec();
        SyncFrameHandler syncFrameHandler = applicationContext.getBean(SyncFrameHandler.class);

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bosses,workers);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ch.pipeline().addLast(
//                        new StringEncoder(),
                        new LengthFieldBasedFrameDecoder(
                        1024,12,
                        4,0,0),
                        loggingHandler,
                        syncFrameCodec,
                        syncFrameHandler);
            }
        });
        bootstrap.bind(port);
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent closedEvent) {
        bosses.shutdownGracefully();
        workers.shutdownGracefully();
        log.debug("同步帧接收服务器停止");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
