package com.wstx.ds.sf.msg;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

@TableName("d_syn_frame")
@EqualsAndHashCode(callSuper = true)
@Data
//@ToString(callSuper = true)
@Accessors(chain = true)
public class SyncFrame extends Message{
    public SyncFrame() {
        this.msgType = specifyType();
    }

    public SyncFrame(String syncCode, Long timestamp, String vehicleSn, String cucsSn, String datalinkId, String missionSn, int firmware, String systemStatus, String safefailure, double longitude, double latitude, double altitudeMsl, float altitudeGnd, float heading, int waypoint) {
        //如果父类没有默认的构造方法，子类必须显示地调用super()并给出参数，让编译器定位到一个合适的构造方法。
        //如果父类有默认构造，子类没有明确的调用父类的构造方法，编译器会自动帮我们加一句super()
//        super();

        this.syncCode = syncCode;
        this.timestamp = timestamp;
        this.vehicleSn = vehicleSn;
        this.cucsSn = cucsSn;
        this.datalinkId = datalinkId;
        this.missionSn = missionSn;
        this.firmware = firmware;
        this.systemStatus = systemStatus;
        this.safefailure = safefailure;
        this.longitude = longitude;
        this.latitude = latitude;
        this.altitudeMsl = altitudeMsl;
        this.altitudeGnd = altitudeGnd;
        this.heading = heading;
        this.waypoint = waypoint;

    }

    // 同步码
    private String syncCode;

    // 时间戳
    private Long timestamp;

    // 无人机编号，16进制数
    private String vehicleSn;

    // 地面站ID，16进制数
    private String cucsSn;

    // 链路ID，16进制数
    private String datalinkId;

    // 飞机飞行任务编号
    private String missionSn;

    // 软件版本
    private int firmware;

    // 系统设备状态码，16进制数
    private String systemStatus;

    // 故障安全字，16进制数
    private String safefailure;

    // 经度
    private double longitude;

    // 纬度
    private double latitude;

    //海高，海拔高度
    private double altitudeMsl;

    //场高，地面高度
    private float altitudeGnd;

    //航向，度
    private float heading;

    //当前航点号
    private int waypoint;

    @Override
    public int specifyType() {
        return Message.SyncFrame;
    }
}
