/**
 * Project Name:Tanker
 * File Name:TrankerInterface.java
 * Package Name:com.szxb.tanker
 * Date:Jun 14, 20177:55:55 PM
 * Copyright (c) 2017, chenzhou1025@126.com All Rights Reserved.
 */

package com.szxb.tanker;

import android.util.Log;

/**
 * ClassName:TrankerInterface <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:   TODO ADD REASON. <br/>
 * Date:     Jun 14, 2017 7:55:55 PM <br/>
 *
 * @author lilei
 * @see
 * @since JDK 1.6
 */
public class TrankerInterface {

    static {
        try {
            System.loadLibrary("Tanker");
        } catch (Throwable e) {
            Log.e("jni", "i can't find Tanker so!");
            e.printStackTrace();
        }
    }


    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String bytesToHexString(byte[] src, int len) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || len <= 0) {
            return null;
        }
        for (int i = 0; i < len; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static String hex2IntString(String hex) {
        int i = Integer.parseInt(hex, 16);
        return String.valueOf(i);
    }


    public static native int getTrankerData(byte[] recv);


    public static String getResult2String(byte[] resultByte) {
        return hex2IntString(bytesToHexString(resultByte, resultByte.length));
    }
}
  
    