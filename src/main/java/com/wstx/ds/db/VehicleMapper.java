package com.wstx.ds.db;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wstx.ds.model.dao.Vehicle;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface VehicleMapper extends BaseMapper<Vehicle> {
}
