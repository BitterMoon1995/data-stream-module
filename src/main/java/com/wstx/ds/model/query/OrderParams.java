package com.wstx.ds.model.query;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhuoda
 * @create: 2020-03-20 09:07 PM from win10
 */

@Slf4j
@Data
public class OrderParams {
    private String column;
    private boolean asc = true;
}
