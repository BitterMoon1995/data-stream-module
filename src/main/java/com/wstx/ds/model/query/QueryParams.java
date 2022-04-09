package com.wstx.ds.model.query;

import lombok.Data;
import java.util.List;

/**
 * 分页基础参数
 *
 * @author lihaifan
 * @Date Created in 2017/10/28 16:19
 */
@Data
public class QueryParams {
    protected String queryType;

    //查询参数
    protected String parameter;

    protected Integer pageNum;

//    @Max(value = 500, message = "每页最大为500")
    protected Integer pageSize;

    //是否查询总量
    protected Boolean ifCount;

    protected List<OrderParams> orderParams;
}
