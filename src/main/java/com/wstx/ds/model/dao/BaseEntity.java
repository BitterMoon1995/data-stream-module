package com.wstx.ds.model.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company java
 * @copyright (c) 2018 javaInc. All rights reserved.
 * @date 2019/3/27 0027 上午 11:15
 * @since JDK1.8
 */
@Data
public class BaseEntity {

    /**
     
* 主键id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}
