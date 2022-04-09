package com.wstx.ds.sf.handler;

import com.wstx.ds.cache.SFCache;
import com.wstx.ds.common.utils.BeanUtils;
import com.wstx.ds.common.utils.MathUtils;
import com.wstx.ds.sf.handler.function.HandlerFunc;
import com.wstx.ds.sf.msg.SyncFrame;
import com.wstx.ds.video.VideoStreamCtrl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@ChannelHandler.Sharable
@Slf4j
public class SyncFrameHandler extends SimpleChannelInboundHandler<SyncFrame> {

    static SFCache sfCache = BeanUtils.getBean(SFCache.class);
    static VideoStreamCtrl videoStreamCtrl = BeanUtils.getBean(VideoStreamCtrl.class);
    private SyncFrame syncFrame;
    private final HandlerFunc func = new HandlerFunc();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SyncFrame syncFrame) throws Exception {

        this.syncFrame = syncFrame;
        System.out.println(syncFrame);
        SyncFrame lastSf = sfCache.querySf(syncFrame.getVehicleSn());
        sfCache.storeSf(syncFrame);
        int i = func.indicateVsStatus(lastSf, syncFrame);

        switch (i){
            //侦测到状态码变动，主动停拉
            case -1:
//                TODO:这些事件啊，还是不要System.out.println，应该写到日志表里面，
//                TODO:大概得有事件主体信息、事件信息、发生日期
                System.out.println("停拉！");
                videoStreamCtrl.stopPull(syncFrame);
                break;
            case 1:
                System.out.println("开拉！");
                videoStreamCtrl.startPull(syncFrame);
        }


        ctx.fireChannelRead(syncFrame);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道活跃！");
        DefaultEventLoopGroup executor = new DefaultEventLoopGroup(1);
        executor.schedule(() -> {
                    //执行上线逻辑
                    sfCache.vehicleOnline(syncFrame.getVehicleSn());
                    //判断机状态码，若机上视频监控开启，执行拉流逻辑
                    int vsStatus = MathUtils.hexToBinary(syncFrame.getSystemStatus()).charAt(7) - 48;
                    if (vsStatus == 1)
                        videoStreamCtrl.startPull(syncFrame);
                },
                1, TimeUnit.SECONDS);
    }

    //同步帧接收超时，被动停拉

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            System.out.println("通道闲置！");
            System.out.println("停拉！");
            sfCache.vehicleOffline(syncFrame.getVehicleSn());
            videoStreamCtrl.stopPull(syncFrame);
            ctx.close().sync();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("无人机主动离线！");
        super.exceptionCaught(ctx, cause);
    }
}
