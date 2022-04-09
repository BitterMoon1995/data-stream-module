package com.wstx.ds.common.utils;

import com.wstx.ds.common.constant.Res;
import com.wstx.ds.common.constant.Status;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpUtils {
    public static Res req(HttpRequestBase httpRequest) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response;
        String respString;

        try {
            response = httpClient.execute(httpRequest);
            InputStream content = response.getEntity().getContent();
            respString = IOUtils.toString(content, StandardCharsets.UTF_8);

        } catch (IOException e) {
            e.printStackTrace();
            return Res.unavailable;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int code = response.getStatusLine().getStatusCode();
        return new Res(new Status(code),respString);
    }

    /*
    屎山写的是api/stop?streamPath=，错的！！！
    但是！！！
    官方文档：/api/gateway/stop?stream=xxx 终止某一个流，入参是流标识（stream）
    也是错的！！！
    最终撞了一下午撞出来是：/api/stop?stream=xxx
    以后还会不会变不知道，但是莫妮卡千万不要更新了

    又但是！！！
    最新版simabuca-gateway的源码里面确实是/api/gateway/stop......，可能还是版本问题
     */
    @Test
    public void testHttpReq() {
        HttpGet httpGet = new HttpGet( "http://127.0.0.1:8090/ds-service/test/nigger");
        Res req = req(httpGet);
        System.out.println(req.getData());
    }
}
