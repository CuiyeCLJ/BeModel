package android.bemodel.com.bemodel.util;


import com.qiniu.util.Auth;

/**
 * Created by Administrator on 2017.09.16.
 */

public class QiniuUtils {

    /**
     * 获取七牛上传凭证uploadtoken
     * @return
     */
    public static String getUploadToken(String bucket) {
        String accessKey = "TmyHXHRtKzgXPJl1DXpsadI66ZgPkJD30gLEPKfS";
        String secretKey = "3LcMf-bkLLwPsM6pNXvUMPuBvxECbGJ1X_Bt1grR";

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        return upToken;
    }

}
