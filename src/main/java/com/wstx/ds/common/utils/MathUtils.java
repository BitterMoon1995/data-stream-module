package com.wstx.ds.common.utils;

public class MathUtils {
    public static String hexToBinary(String hexStr){
        String firstTwo = hexStr.substring(0, 2);
        if (firstTwo.equals("0x") || firstTwo.equals("0X"))
            hexStr = hexStr.substring(2);
        int decimal = Integer.parseInt(hexStr, 16);
        return Integer.toBinaryString(decimal);
    }
}
