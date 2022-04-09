package com.wstx.ds.controller;

import com.wstx.ds.model.dto.SfDto;
import com.wstx.ds.model.query.SfQuery;
import com.wstx.ds.service.SyncFrameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class SyncFrameController {

    @Autowired
    SyncFrameService service;

    @GetMapping("/fleet/all")
    public List<List<SfDto>> getFleet(){
        ArrayList<List<SfDto>> list = new ArrayList<>();
        list.add(service.getFleetOnline());
        list.add(service.getFleetOffline());
        return list;
    }

    @GetMapping("/fleet/online")
    public List<SfDto> getFleetOnline(){
        return service.getFleetOnline();
    }

    @GetMapping("/fleet/offline")
    public List<SfDto> getFleetOffline(){
        return service.getFleetOffline();
    }

    @PostMapping("/sf/vsn")
    public SfDto getSfByVsn(@RequestBody SfQuery sfQuery){
        return service.getSfByVsn(sfQuery.getParameter());
    }
}
