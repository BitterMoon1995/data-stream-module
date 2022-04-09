package com.wstx.ds;

import com.wstx.ds.model.dao.Vehicle;
import com.wstx.ds.model.dto.PageQueryRes;
import com.wstx.ds.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CrudTest {

    @Autowired
    VehicleService vehicleService;

    @Test
    public void test() {
        PageQueryRes<Vehicle> res = vehicleService.queryFleetOnline(null);
        System.out.println(res);
    }
}
