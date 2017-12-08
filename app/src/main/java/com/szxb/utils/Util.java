package com.szxb.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.szxb.App;
import com.szxb.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

/**
 * 作者：Tangren on 2017/6/8 13:04
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */
public class Util {

    private static final String TAG = "Util";

    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");


    /**
     * 订单号
     *
     * @return orderNo
     */
    public static String getOrderNo() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", new Locale("zh", "CN"));
        return "gas" + format.format(new Date()) + buildRadom(5);
    }

    public static int buildRadom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1)
            random = random + 0.1;
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) (random * num);
    }

    public static Bitmap CreateCode(String str) {
        if (TextUtils.isEmpty(str))
            return BitmapFactory.decodeResource(App.getInstance().getResources(), R.mipmap.load_fail);
        Bitmap bitmap = null;
        BitMatrix matrix = null;
        try {
            matrix = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, 300, 300);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            // 二维矩阵转为一维像素数组,也就是一直横着排了
            int[] pix = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pix[y * width + x] = 0xff000000;
                    }
                }
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pix, 0, width, 0, 0, width, height);
        } catch (WriterException e) {
            e.printStackTrace();
            Log.e(TAG, "CreateCode: " + e.getMessage());
            return BitmapFactory.decodeResource(App.getInstance().getResources(), R.mipmap.load_fail);
        }

        return bitmap;

    }


    public static Bitmap Create2DCode(String str, int width, int height) {
        try {
            Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
            hints.put(EncodeHintType.MARGIN, 2);
            BitMatrix matrix = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, width, height);
            matrix = deleteWhite(matrix);//删除白边
            width = matrix.getWidth();
            height = matrix.getHeight();
            int[] pixels = new int[width * height];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = Color.BLACK;
                    } else {
                        pixels[y * width + x] = Color.WHITE;
                    }
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
            return bitmap;
        } catch (Exception e) {
            return BitmapFactory.decodeResource(App.getInstance().getResources(), R.mipmap.load_fail);
        }
    }


    private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;

        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1]))
                    resMatrix.set(i, j);
            }
        }
        return resMatrix;
    }


    /**
     * 检查字符串是否可以转化成数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (Util.isEmpty(str)) {
            return false;
        } else {
            char[] chars = str.toCharArray();
            int sz = chars.length;
            boolean hasExp = false;
            boolean hasDecPoint = false;
            boolean allowSigns = false;
            boolean foundDigit = false;
            int start = chars[0] == 45 ? 1 : 0;
            int i;
            if (sz > start + 1 && chars[start] == 48 && chars[start + 1] == 120) {
                i = start + 2;
                if (i == sz) {
                    return false;
                } else {
                    while (i < chars.length) {
                        if ((chars[i] < 48 || chars[i] > 57) && (chars[i] < 97 || chars[i] > 102) && (chars[i] < 65 || chars[i] > 70)) {
                            return false;
                        }

                        ++i;
                    }

                    return true;
                }
            } else {
                --sz;

                for (i = start; i < sz || i < sz + 1 && allowSigns && !foundDigit; ++i) {
                    if (chars[i] >= 48 && chars[i] <= 57) {
                        foundDigit = true;
                        allowSigns = false;
                    } else if (chars[i] == 46) {
                        if (hasDecPoint || hasExp) {
                            return false;
                        }

                        hasDecPoint = true;
                    } else if (chars[i] != 101 && chars[i] != 69) {
                        if (chars[i] != 43 && chars[i] != 45) {
                            return false;
                        }

                        if (!allowSigns) {
                            return false;
                        }

                        allowSigns = false;
                        foundDigit = false;
                    } else {
                        if (hasExp) {
                            return false;
                        }

                        if (!foundDigit) {
                            return false;
                        }

                        hasExp = true;
                        allowSigns = true;
                    }
                }

                return i < chars.length ? (chars[i] >= 48 && chars[i] <= 57 ? true : (chars[i] != 101 && chars[i] != 69 ? (chars[i] == 46 ? (!hasDecPoint && !hasExp ? foundDigit : false) : (allowSigns || chars[i] != 100 && chars[i] != 68 && chars[i] != 102 && chars[i] != 70 ? (chars[i] != 108 && chars[i] != 76 ? false : foundDigit && !hasExp && !hasDecPoint) : foundDigit)) : false)) : !allowSigns && foundDigit;
            }
        }
    }


    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }


    /**
     * String转int
     *
     * @param str
     * @return
     */
    public static Integer String2Int(String str) {
        if (isNumber(str)) {
            return Integer.valueOf(str);
        }
        return 0;
    }


    /**
     * 保留两位小数
     *
     * @param str
     * @return
     */
    public static String String2Float2String(String str) {
        int i = String2Int(str);
        return decimalFormat.format((float) i / (float) 100);
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("zh", "CN"));

    /**
     * 当前日期
     *
     * @return getCurrentTime
     */
    public static String getCurrentTime() {
        return format.format(new Date());
    }

    /**
     * 七天前的日期
     *
     * @return get7BeforeTime
     */
    public static String get7BeforeTime(int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, value);
        return format.format(calendar.getTime());
    }

    /**
     * 删除
     *
     * @param textView te
     */
    public static void delNum(TextView textView) {
        String result = textView.getText().toString();
        if (result.equals(""))
            return;
        result = result.substring(0, result.length() - 1);
        textView.setText(result);
    }


    /**
     * 旧
     * @param bytes
     * @return
     */
    public static String bcd2Str2(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    /**
     * 新
     * @param bytes
     * @return
     */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    //加油总量/100
    public static String getFuelingUp(String fuelingUp) {
        if (isNumber(fuelingUp)) {
            double flu = (Double.valueOf(fuelingUp))/100;
            return decimalFormat.format(flu);
        }
        return fuelingUp;
    }

    public static String getMoney(String money) {
        if (isNumber(money)) {
            Log.d("Util",
                "getMoney(Util.java:330)");
            double flu = (Double.valueOf(money))/100;
            return decimalFormat.format(flu);
        }
        return money;
    }
}
