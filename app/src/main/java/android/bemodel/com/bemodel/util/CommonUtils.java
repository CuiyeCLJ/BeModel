package android.bemodel.com.bemodel.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017.10.14.
 */

public class CommonUtils {

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

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo();
    }
}
