package com.wstx.ds.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wstx.ds.cache.SFCache;
import com.wstx.ds.common.utils.QueryUtils;
import com.wstx.ds.db.VehicleMapper;
import com.wstx.ds.model.dao.Vehicle;
import com.wstx.ds.model.dto.PageQueryRes;
import com.wstx.ds.model.dto.SfDto;
import com.wstx.ds.model.query.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    SFCache sfCache;
    VehicleMapper mapper;
    SyncFrameService sfService;

    @Autowired
    public VehicleService(SFCache sfCache, VehicleMapper mapper, SyncFrameService sfService) {
        this.sfCache = sfCache;
        this.mapper = mapper;
        this.sfService = sfService;
    }

    public PageQueryRes<Vehicle> queryFleetOnline(QueryParams queryParams){
        List<SfDto> onlineSfList = sfCache.getDtoList(SFCache.onlineFleet);
        List<Vehicle> onlineVhList = new ArrayList<>();
        for (SfDto sfDto : onlineSfList) {
            Vehicle vehicle = mapper.selectOne(new QueryWrapper<Vehicle>()
                    .eq("vehicle_sn", sfDto.getVehicleSn()));
            if (vehicle != null) {
                onlineVhList.add(vehicle);
            }
        }
        return QueryUtils.genPqRes(onlineVhList);
    }

    /*
    再强调一遍离线无人机是录入过且上线过的，乱录入的或者刚录入的不考虑
     */
    public PageQueryRes<Vehicle> queryFleetOffline(QueryParams queryParams){
        return null;
    }

    public PageQueryRes<Vehicle> queryFleet(QueryParams queryParams) {
        return null;
    }
}
