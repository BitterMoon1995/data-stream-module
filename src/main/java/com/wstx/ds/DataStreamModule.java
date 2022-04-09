package com.wstx.ds;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wstx.ds.db")
public class DataStreamModule {

//    @Autowired
//    static SFCache redisService;

    public static void main(String[] args) {
//        redisService.initVehicleSet();
        SpringApplication.run(DataStreamModule.class, args);
    }

}
