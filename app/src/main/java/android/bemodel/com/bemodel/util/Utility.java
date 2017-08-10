package android.bemodel.com.bemodel.util;

import android.bemodel.com.bemodel.db.ModelCircleInfo;
import android.graphics.Bitmap;
import android.icu.text.RelativeDateTimeFormatter;
import android.provider.ContactsContract;
import android.widget.AbsListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.nio.channels.Pipe;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.crypto.Mac;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

import static android.R.attr.data;
import static android.R.attr.theme;
import static android.R.attr.timePickerDialogTheme;

/**
 * Created by Administrator on 2017.07.25.
 */

public class Utility {

    /**
     * 获取服务器时间
     */
    static String times;

    public static String getServerTime() {
        Bmob.getServerTime(new QueryListener<Long>() {
            @Override
            public void done(Long aLong, BmobException e) {
                if (e == null) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    times = formatter.format(new Date(aLong * 1000L));
                } else {
                    times = e.getMessage();
                }
            }
        });
        return times;
    }

    /**
     * 把Bitmap转Byte
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 获取文件名
     * @return
     */
    public static String getRandomFileName() {
        //获取当前时间
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddhhmmss");
        String date= simpleDateFormat.format(new Date());
        //获取四位随机数
        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;
        String number = String.valueOf(randomNumber);
        //拼接当前时间和随机数
        StringBuilder stringBuilder = new StringBuilder(date);
        stringBuilder.append(number);
        return stringBuilder.toString();
    }

    /*
    public static String uploadToken(String bucket, String accessKey, String secretKey){
        PutPolicy upPolicy = new PutPolicy(bucket);
        upPolicy.endUser = user;
        upPolicy.callbackUrl = callbackUrl;
        upPolicy.callbackBody = "key=$(key)&hash=$(etag)&width=$(imageInfo.width)&height=$(imageInfo.height)&user=$(endUser)&size=$(fsize)&mime=$(mimeType)";
        String token = null;
        Mac mac = new Mac(accessKey, secretKey);
        try{
            token = upPolicy.token(mac);
        }catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }
     */

}



























