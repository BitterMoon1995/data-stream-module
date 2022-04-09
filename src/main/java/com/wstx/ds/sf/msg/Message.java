package com.wstx.ds.sf.msg;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public abstract class Message implements Serializable {

    @TableId(type = IdType.AUTO)
    public Integer Id;

    @TableField(exist = false)
    protected int sequenceNum;
    @TableField(exist = false)
    protected int msgType;

    public static final int LoginRequest = 0;
    public static final int SyncFrame = 1;
    public static final int OperationFrame = 2;

    //重写默认构造
    public Message() {
        this.msgType = specifyType();
    }

    public abstract int specifyType();

}
