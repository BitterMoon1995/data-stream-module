package com.wstx.ds.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wstx.ds.cache.SFCache;
import com.wstx.ds.common.utils.Converter;
import com.wstx.ds.db.SyncFrameMapper;
import com.wstx.ds.model.dto.SfDto;
import com.wstx.ds.sf.msg.SyncFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SyncFrameService {
    @Autowired
    SFCache cache;
    @Autowired
    SyncFrameMapper mapper;

    public List<SfDto> getFleetOnline(){
        return cache.getDtoList(SFCache.onlineFleet);
    }

    public List<SfDto> getFleetOffline(){
        return cache.getDtoList(SFCache.offlineFleet);
    }

    public SfDto getSfByVsn(String vsn) {
        SyncFrame syncFrame = cache.querySf(vsn);
        return Converter.sfToDto(syncFrame);
    }
}
