package com.wstx.ds;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.wstx.ds.common.constant.Res;
import com.wstx.ds.common.utils.TestUtils;
import com.wstx.ds.db.VehicleMapper;
import com.wstx.ds.model.dao.Vehicle;
import com.wstx.ds.sf.msg.SyncFrame;
import com.wstx.ds.video.VideoStreamCtrl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VideoStreamCtlTest {

    @Autowired
    VideoStreamCtrl videoStreamCtrl;

    @Autowired
    VehicleMapper vehicleMapper;

    @Test
    public void test() {
        SyncFrame syncFrame = TestUtils.genSF("0x4397", 0.89, 0.98);
        syncFrame.setMissionSn("0x01010001");
//        videoStreamCtrl.startPull(syncFrame);
        videoStreamCtrl.stopPull(syncFrame);
    }

    @Test
    public void test2() {
        SyncFrame syncFrame = new SyncFrame().setVehicleSn("0x4397");
        vehicleMapper.update(new Vehicle().setOuterRtmp("none"),
                new UpdateWrapper<Vehicle>().eq("vehicle_sn",syncFrame.getVehicleSn()));
    }
}
