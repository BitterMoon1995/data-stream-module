package com.wstx.ds.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageQueryRes<T> {

    /**
     * 总记录数
     */
    private Long total;

    /*
    结果集
     */
    private List<T> resList;
}
