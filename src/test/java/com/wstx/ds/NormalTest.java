package com.wstx.ds;

import com.wstx.ds.common.utils.MathUtils;
import org.junit.jupiter.api.Test;

import java.util.Locale;

public class NormalTest {
    @Test
    public void test() {
        String s = MathUtils.hexToBinary("0xFF");
        int i = s.charAt(7) - 48;
        System.out.println(s.charAt(7) - 48);
    }

    @Test
    public void test2() {
        int i = 1;
        switch (i){
            case -1:
                System.out.println("停拉！");
                System.out.println("停拉！");
                System.out.println("停拉！");

                break;
            case 1:
                System.out.println("停拉！");
                System.out.println("停拉！");

                System.out.println("开拉！");
        }
    }
}
