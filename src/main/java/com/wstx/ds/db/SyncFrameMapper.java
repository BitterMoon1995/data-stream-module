package com.wstx.ds.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wstx.ds.sf.msg.SyncFrame;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SyncFrameMapper extends BaseMapper<SyncFrame> {
}
