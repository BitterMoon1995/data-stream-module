package com.wstx.ds.common.utils;

import com.wstx.ds.model.dto.SfDto;
import com.wstx.ds.sf.msg.SyncFrame;
import org.junit.jupiter.api.Test;

public class Converter {
    public static SfDto sfToDto(SyncFrame syncFrame){
        SfDto dto = new SfDto();
        dto.setVehicleSn(syncFrame.getVehicleSn())
                .setLatitude(syncFrame.getLatitude())
                .setLongitude(syncFrame.getLongitude())
                .setAltitudeGnd(syncFrame.getAltitudeGnd())
                .setTimestamp(syncFrame.getTimestamp());
        return dto;
    }
}
