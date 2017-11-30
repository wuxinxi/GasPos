package com.szxb.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.szxb.App;

import java.util.List;

/**
 * 作者: Tangren on 2017/8/7
 * 包名：com.szxb.utils
 * 邮箱：996489865@qq.com
 * TODO:判断是否是DEBUG模式
 */

public class AppUtil {

    private static Boolean isDebug = null;

    public static boolean isDebug() {
        return isDebug == null ? false : isDebug;
    }

    public static void syncISDebug(Context context) {
        if (isDebug == null) {
            isDebug = context.getApplicationInfo() != null && (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        }
    }

    /**
     * 获取当前版本号
     *
     * @param context .
     * @return .
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    public static boolean isForeground(String className) {
        if (TextUtils.isEmpty(className)) {
            return false;
        }
        ActivityManager am = (ActivityManager) App.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static final String AL_APPID = "24658003-1";

    public static final String AL_APPSECRET = "f15435c5b99c7e845e3dee5d2981dc29";

    public static final String AL_RSASECRET = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCXmqqcVzfc0QBcSuW1cVrhX+fDSzIg4O8agzSx1COZD3HflWQ5Ktb+JAeZa3bKmdjQLDlNAc5su5TVt7lcCdxiPDBc5okPWMGJT22jKyIS1V5qBx+mthsW1BZ9ZAeeHD+DBh3rSGrvTDqmL8PZdlyEkQ0p7SWirhN2NLrNd7mX7x2+YhURYkkVVSjVO8tQeluyGSrOH2EGeFwLk+z9U9Pw9r+WZ7rN0jFd29DkuXXVRQNqEim84HvWV7LmdReoYP0Z2p3rz+i4Xw0x8/xSmp1IsLBeE1WcaTDEkEMyKYBENe0Pvjm5mXOapITdGwN4FvxQQJjFx1HioEZVFoOQf753AgMBAAECggEADVYArhIKCvWjp3q7LDPVf7/U/lvuA/BleBqUlxQHMZ6nMLjOgSzgjqiSto86euX/qYhKXhjLlMsHc6let9byOP38bx+kbOIs3CxNg632qFt2m7MWuK6KQX7Un6Ed/m2TtK3kEd9j8Lru6dGH0+hSiSLNWqPOzLmtYpeO2yYuAExYufifS0JFUeqFf+pBgPbmTNAvkj9kpJpyeFKMPJZMWjy86/J5NRu5l1f6v4dDGOXV65rpmy3i1CjhCANmoUH1EQDvTtgqxwGGahvaNuPWfIrIAmp0Bq7JlLNdp9G7zY3ACDBwp3t6qOj+lCaQQF3rGFSBBdjI48Wuxk6SVIZwcQKBgQDUjTuuuaPWqEPsJKqUjrZpHLhnypkpPtYPmU39GcWY2BMYcvSdWmW+qy2KDqB0uXE6iNPdySPNUvXa3SuQDRQn1/RE6haVBq7ydClqkefn97NA1KLBgQzMDexLFeluyL/wzbiUt+hdKfBVEJkiKeEHlpI42UfOY97gNC8k6X4tqQKBgQC2mBFANA+YYoF6Cg5Uw/fB7WnNskNY84DE10u6uqJOFERco60ljSM8blCTgLRlrlNIN2XxF/NM85CBidK3Ithi6fnfWCk/IGDdnFd/0v2xb6meBWaF73OmgFWfEXycPOmuX2XVrc2XBb7Sij9Xmx0+6qJek3kKxnIQ00RT0CLfHwKBgF1aaowjHMJ6xNpCgGkfBR7Fbninkv16i6nuO0KevxqSTFDArI8v1OAZbrbkFbUufXyn++W9IexcM38dBLUQCXo2byKVbbWmadAzFL2pt6E6OZCP06wJcgVtWAriZNQzd5nbwOYQoA3LDbNOxvMqUZtzlmVBiTgsxQbT5KDhrHmBAoGAZJ18/AT0BwNY13fNF9Z4ossBP1CYnd5oOY9tcnK/VwkB9o5WrdeM8BLVocINJe6eg6/LI8QP1HpuSYM5sqKn3DGT+AdrixZ7agJpkM0h8HFYZPQS34DoGHqlM6b+JFXIasa0pb9FB5Ap63g0mUCnJ/FBEZ5v14ksd6sC/rA1Vv8CgYARklIx06VgVZmLYE1opG4SaIfL+UqeDwkYChJegAmBqzIXwSrMzFspFQmQH0t1EhnvgDYQzsRWz/CJ1Wv41fuNzksqhQiKR6kcx+RMgbsp8DMojLjOOwFInisBamOCGpz2ZSvprNGhyxjbke/Tm2lcFBIY4OtkIsFuGtv10MCP6A==";
}
