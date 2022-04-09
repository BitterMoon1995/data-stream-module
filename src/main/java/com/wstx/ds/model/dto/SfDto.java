package com.wstx.ds.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SfDto {

    private String vehicleSn;

    private double longitude;

    private double latitude;

    private float altitudeGnd;

    private Long timestamp;

    //--------------- 第一版 2021.9.15
}
