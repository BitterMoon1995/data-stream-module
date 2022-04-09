package com.wstx.ds.video;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wstx.ds.common.constant.Res;
import com.wstx.ds.common.utils.HttpUtils;
import com.wstx.ds.db.VehicleMapper;
import com.wstx.ds.model.dao.Vehicle;
import com.wstx.ds.sf.msg.SyncFrame;
import org.apache.http.client.methods.HttpGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoStreamCtrl {

    @Value("${monica.gateway.listenAddr}")
    String monicaGateway;
    @Value("${monica.jessica.listenAddr}")
    String jessicaAddr;
    @Autowired
    VehicleMapper vehicleMapper;

    public void startPull(SyncFrame syncFrame) {
        QueryWrapper<Vehicle> query = new QueryWrapper<>();
        query.eq("vehicle_sn", syncFrame.getVehicleSn());

        Vehicle entity = vehicleMapper.selectOne(query);
        if (entity == null) {
            System.out.println("无此无人机！");
            return;
        }

        String rtspUrl = entity.getInnerRtsp();
        String streamPath = genStreamPath(syncFrame);
        HttpGet httpGet = new HttpGet(monicaGateway + "/rtsp/pull?target="
                + rtspUrl + "&streamPath=" + streamPath);

        Res res = HttpUtils.req(httpGet);
        System.out.println(res.getData());
        if (res.getStatus().getCode() != 200)
            System.out.println("拉流失败！");
        /*
        坑点：生成streamPath，前面不加 / ，因为是等号连接的。但是数据库录入直播地址要加一个 /。
        另外/jessica是不需要的
         */
        else {
            System.out.println("拉流成功！");
            //拉成功了后把流路径同步到DB
            vehicleMapper.updateById(new Vehicle()
                    .setId(entity.getId())
                    .setOuterRtmp(
                            jessicaAddr + "/" + streamPath));
        }
    }

    public void stopPull(SyncFrame syncFrame) {
        HttpGet httpGet = new HttpGet(
                monicaGateway + "/api/stop?stream=" + genStreamPath(syncFrame));
        Res res = HttpUtils.req(httpGet);

        //停拉后，将DB中该无人机直播流路径置为空，这里设空为"none"
        //这样可以快捷判断无人机是否处于任务中且开启视频流
        vehicleMapper.update(new Vehicle().setOuterRtmp("none"),
                new UpdateWrapper<Vehicle>().eq("vehicle_sn",syncFrame.getVehicleSn()));

        System.out.println(res.getData());
        if (res.getStatus().getCode() != 200)
            System.out.println("停止拉流失败！");
    }

    //TODO:这个得加密！不然无人机编号、任务编号全在URL里面暴露了！
    public String genStreamPath(SyncFrame syncFrame) {
        return "" + syncFrame.getVehicleSn() +
                "/" + syncFrame.getMissionSn();
    }
}
/*
Jessica地址：ws://localhost:8089/jessica/nigger/black
//        String rtspUrl = "rtsp://admin:RBYWEQ@192.168.20.248:554/h264/ch1/main/av_stream";
//        String streamPath = "nigger/black2";
 */