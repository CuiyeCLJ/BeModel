package android.bemodel.com.bemodel.util;

import android.os.Environment;

/**
 * SD卡相关工具类
 * Created by Zheng Lifu on 2017.10.17.
 */

public class SDCardUtils {
    /**
     * 检查SD卡是否存在
     * @return
     */
    public static boolean checkSdCard() {
        if (android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
