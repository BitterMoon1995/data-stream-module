package com.wstx.ds.cache;

import com.alibaba.fastjson.JSON;
import com.wstx.ds.common.utils.Converter;
import com.wstx.ds.common.utils.Printer;
import com.wstx.ds.db.SyncFrameMapper;
import com.wstx.ds.db.VehicleMapper;
import com.wstx.ds.model.dao.Vehicle;
import com.wstx.ds.model.dto.SfDto;
import com.wstx.ds.sf.msg.SyncFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class SFCache {
    //onlineVehiclesZSet 存储已上线的无人机序列号的ZSet
    public static final String onlineFleet = "fleet:online";
    public static final String offlineFleet = "fleet:offline";
    public static final String entireFleet = "fleet:all";
    //synchronized frame 无人机同步帧String
    public static final String syncFrameKey = "sf:";

    public static final String trackPointListKey = "tpl:";

    @Autowired
    RedisTemplate<String,Object> redis;
    @Autowired
    SyncFrameMapper sfMapper;
    @Autowired
    VehicleMapper vehicleMapper;

    public RedisTemplate<String,Object> getRedis(){
        return redis;
    }

    //存储同步帧。不设置过期，因为离线无人机的位置信息也得显示
    public void storeSf(SyncFrame syncFrame){
        String vehicleSn = syncFrame.getVehicleSn();
        String jsonString = JSON.toJSONString(syncFrame);
        redis.opsForValue().set(syncFrameKey + vehicleSn,jsonString);
    }

    //无人机上线。
    public void vehicleOnline(String vehicleSn){
        long curSecs = System.currentTimeMillis() / 1000;
        redis.opsForSet().remove(offlineFleet,vehicleSn);
        redis.opsForZSet().add(onlineFleet,vehicleSn,curSecs);
    }

    //无人机下线
    public void vehicleOffline(String vehicleSn){
        redis.opsForZSet().remove(onlineFleet,vehicleSn);
        redis.opsForSet().add(offlineFleet,vehicleSn);
    }

    //初始化所有无人机的序列号到Redis中
    @PostConstruct
    public void initVehicleSet(){
        Printer.print("初始化数据库中无人机序列号列表");
        List<Vehicle> vehicleList = vehicleMapper.selectList(null);
        for (Vehicle vehicle : vehicleList) {
            redis.opsForSet().add(entireFleet, vehicle.getVehicleSn());
        }
    }

    public List<SfDto> getDtoList(String key){
        Set<Object> fleetSnSet = null;
        //在线机群用的ZSet，离线机群Set，不一样
        if (key.equals(offlineFleet))
            fleetSnSet = redis.opsForSet().members(key);
        else if (key.equals(onlineFleet))
            fleetSnSet = redis.opsForZSet().reverseRangeByLex(key, RedisZSetCommands.Range.unbounded());
        //没有该状态（上线/离线）的无人机
        if (Objects.isNull(fleetSnSet))
            return null;

        ArrayList<String> fleetSnList = new ArrayList<>();
        for (Object o : fleetSnSet) {
            fleetSnList.add(syncFrameKey+o.toString());
        }

        List<Object> sfs = redis.opsForValue().multiGet(fleetSnList);
        //从业务上来说不可能为空，已经进了onlineZSet或offlineSet就一定会存在同步帧
        assert sfs != null;
        ArrayList<SfDto> resList = new ArrayList<>();
        for (Object sf : sfs) {
            SyncFrame syncFrame = JSON.parseObject(sf.toString(), SyncFrame.class);
            resList.add(Converter.sfToDto(syncFrame));
        }
        return resList;
    }

    public SyncFrame querySf(String vehicleSn){
        Object o = redis.opsForValue().get(syncFrameKey + vehicleSn);
        if (o == null)
            return null;
        return JSON.parseObject(o.toString(), SyncFrame.class);
    }

}
