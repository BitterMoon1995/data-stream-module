package com.wstx.ds.sf.client;

import com.wstx.ds.common.utils.TestUtils;
import com.wstx.ds.sf.msg.SyncFrame;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;

public class ShenzhouV {
    @Test
    //一上一下
    public void test() throws IOException, InterruptedException {
        byte[] bytes1 = ClientFunction.getEncodedBA("0X01010006");
        byte[] bytes2 = ClientFunction.getEncodedBA("0X01014397");

        //周神一号：一直上线
        new Thread(()->{
            Socket socket;
            try {
                socket = new Socket("127.0.0.1",9966);
                OutputStream oStream = socket.getOutputStream();
                for (int i = 0; i < 1800; i++) {
                    oStream.write(bytes1);
                    oStream.flush();
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //周神二号：上线15秒下线
//        new Thread(()->{
//            Socket socket;
//            try {
//                socket = new Socket("127.0.0.1",9966);
//                OutputStream oStream = socket.getOutputStream();
//                for (int i = 0; i < 15; i++) {
//                    oStream.write(bytes2);
//                    oStream.flush();
//                    Thread.sleep(1000);
//                }
//                Thread.sleep(1000 * 60 * 60 * 24);
//            } catch (IOException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();

        Thread.sleep(1000 * 60 * 60 * 24);
    }

    @Test
    /*
    1958街正中间出发，东——》南——》西——》北，最后返回1958，历时80秒
    泪目！
    经度longitude变化幅度：每秒0.001 至东到104.77000
    纬度latitude变化幅度：每秒0.001 至南到31.46250
     */
    public void travelAroundMianyang() throws InterruptedException, IOException {
        String sn = "0x4397";

        BigDecimal longitude = BigDecimal.valueOf(104.75000);
        BigDecimal latitude = BigDecimal.valueOf(31.48250);
        BigDecimal changeVal = BigDecimal.valueOf(0.001);

        Socket socket = new Socket("127.0.0.1", 9966);
        OutputStream oStream = socket.getOutputStream();

        //noinspection InfiniteLoopStatement
        while (true){
            for (int i = 0; i < 20; i++) {
                longitude = longitude.add(changeVal);
                SyncFrame sf = TestUtils.genSF(sn, longitude.doubleValue(), latitude.doubleValue());
                byte[] bytes = ClientFunction.genSfBytes(sf);
                oStream.write(bytes);
                oStream.flush();
                Thread.sleep(1000);
            }
            for (int i = 0; i < 20; i++) {
                latitude = latitude.subtract(changeVal);
                SyncFrame sf = TestUtils.genSF(sn, longitude.doubleValue(), latitude.doubleValue());
                byte[] bytes = ClientFunction.genSfBytes(sf);
                oStream.write(bytes);
                oStream.flush();
                Thread.sleep(1000);
            }
            for (int i = 0; i < 20; i++) {
                longitude = longitude.subtract(changeVal);
                SyncFrame sf = TestUtils.genSF(sn, longitude.doubleValue(), latitude.doubleValue());
                byte[] bytes = ClientFunction.genSfBytes(sf);
                oStream.write(bytes);
                oStream.flush();
                Thread.sleep(1000);
            }
            for (int i = 0; i < 20; i++) {
                latitude = latitude.add(changeVal);
                SyncFrame sf = TestUtils.genSF(sn, longitude.doubleValue(), latitude.doubleValue());
                byte[] bytes = ClientFunction.genSfBytes(sf);
                oStream.write(bytes);
                oStream.flush();
                Thread.sleep(1000);
            }
        }

//        Thread.sleep(1000 * 60 * 60 * 24);
    }


}
