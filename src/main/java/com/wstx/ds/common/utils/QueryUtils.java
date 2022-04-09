package com.wstx.ds.common.utils;

import com.wstx.ds.model.dao.Vehicle;
import com.wstx.ds.model.dto.PageQueryRes;

import java.util.List;

public class QueryUtils {
    //不排序不分页操他妈死逼
    public static <T> PageQueryRes<T> genPqRes(List<T> list){
        PageQueryRes<T> res = new PageQueryRes<T>();
        res.setTotal((long) list.size());
        res.setResList(list);
        return res;
    }
}
