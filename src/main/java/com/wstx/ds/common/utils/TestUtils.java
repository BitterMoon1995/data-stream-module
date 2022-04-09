package com.wstx.ds.common.utils;

import com.wstx.ds.sf.msg.SyncFrame;

public class TestUtils {
    public static SyncFrame genSF(String sn,double longitude, double latitude){
        return new SyncFrame(sn, System.currentTimeMillis(),
                sn, "0x01010001", "0x0101",
                "0x01010001", 100, "0xFF", "0xFF",
                longitude, latitude, 490.5,
                50.5f, 60.5f, 124);
    }
}
