package com.wstx.ds.sf.handler.function;

import com.wstx.ds.common.utils.MathUtils;
import com.wstx.ds.sf.msg.SyncFrame;

public class HandlerFunc {
    //判断下行视频流（video steam）状态
    public int indicateVsStatus(SyncFrame old, SyncFrame cur) {
        char curStatusChar = MathUtils.hexToBinary(cur.getSystemStatus()).charAt(7);
        if (old == null)
        return curStatusChar - 48;

        return curStatusChar
                -
                MathUtils.hexToBinary(old.getSystemStatus()).charAt(7);
    }

}
