package android.bemodel.com.bemodel.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by Zheng Lifu on 2017.10.16.
 */

public class NetworkUtils {

    private static NetworkInfo activeNetworkInfo;


    /**
     * 获取活动网络信息
     * @return
     */
    public static NetworkInfo getActiveNetworkInfo() {
        return ((ConnectivityManager) Utils.getApp().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    /**
     * 判断网络是否连接
     *
     * @return true：是  false：否
     */
    public static boolean isConnected() {
        NetworkInfo networkInfo = getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * 判断移动数据是否打开
     * @return  true：是  false：否
     */
    public static boolean getDataEnabled() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager)Utils.getApp().getSystemService(Context.TELECOM_SERVICE);
            Method getMobileDataEnabledMethod = telephonyManager.getClass().getDeclaredMethod("getDataEnabled");
            if (getMobileDataEnabledMethod != null) {
                return (boolean)getMobileDataEnabledMethod.invoke(telephonyManager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 判断wifi是否打开
     * @return
     */
    public static boolean getWifiEnable() {
        @SuppressLint("WifiManagerLeak")
        WifiManager wifiManager = (WifiManager)Utils.getApp().getSystemService(Context.WIFI_SERVICE);
        return wifiManager.isWifiEnabled();
    }


    /**
     * 检查是否有网络
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * @param context
     * @return
     */
    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 检查是否是WIFI
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
        }
        return false;
    }

    /**
     * 检查是否是移动网络
     * @param context
     * @return
     */
    public static boolean isMobile(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

}
