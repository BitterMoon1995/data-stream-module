package com.wstx.ds.model.dao;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 无人机表
 *
 * @author t_vehicle
 */
@Data
@TableName("t_vehicle")
@Accessors(chain = true)

public class Vehicle {

	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 无人机编号
	 */	
	private String vehicleSn;
	
	 /**
             * 索引别名
     */
	private String alias;
	
	/**
	 * 地面站编号
	 */
	private String cucsSn;

	/**
	 * 内网rtsp地址
	 */
	private String innerRtsp;
	
	/**
	 * 外网rtmp地址
	 */
	private String outerRtmp;
	
    
}
