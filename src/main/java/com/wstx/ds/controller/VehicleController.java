package com.wstx.ds.controller;

import com.wstx.ds.model.dao.Vehicle;
import com.wstx.ds.model.dto.PageQueryRes;
import com.wstx.ds.model.query.QueryParams;
import com.wstx.ds.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class VehicleController {

    @Autowired
    VehicleService service;

    /*
    TODO:
    不管你分不分页都要分页查询；
    不管你查单个还是查多个都要查多个；
    自己节后改
    QueryParams OrderItemDTO还得引入进来，莫法
     */
    @PostMapping("/vehicle/fleetQuery")
    //地狱知识：@RequestBody只支持前端用POST方式发JSON格式的数据
    public PageQueryRes<Vehicle> fleetQuery(@RequestBody QueryParams param) {
        System.out.println(param);
        switch (param.getQueryType()) {
            //查询在线机群，不分页
            case "online":
                return service.queryFleetOnline(param);
            case "offline":
                return service.queryFleetOffline(param);
            case "all":
                return service.queryFleet(param);
            default:
                return null;
        }
    }

}
