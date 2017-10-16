package android.bemodel.com.bemodel.util;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017.07.25.
 */

public class Utility {

    static double DEF_PI180= 0.01745329252;     // PI/180.0
    static double DEF_R =6370693.5;     // 地球半径/m
    static String times;

    /**
     * 获取服务器时间
     */
    /*public static String getServerTime() {
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
    }*/

    /**
     * 计算两个时间差
     * @param startDate
     * @return
     */
    public static String getTimeDifference(Date startDate) {

        Date nowDate = Calendar.getInstance().getTime();

        if(startDate == null || nowDate == null){
            return null;
        }

        long timeLong = nowDate.getTime() - startDate.getTime();

        if (timeLong < 1000*60){
            return timeLong/1000 + "秒前";

        } else if (timeLong < 1000*60*60){
            timeLong = timeLong/1000/60;
            return timeLong + "分钟前";

        } else if (timeLong < 1000*60*60*24){
            timeLong = timeLong/1000/60/60;
            return timeLong+"小时前";

        } else if (timeLong < 1000*60*60*24*7){
            timeLong = timeLong/1000/60/60/24;
            return timeLong + "天前";

        } else if (timeLong < 1000*60*60*24*7*4){
            timeLong = timeLong/1000/60/60/24/7;
            return timeLong + "周前";

        }  else if (timeLong < 1000*60*60*24*30*12) {
            timeLong = timeLong/1000/60/60/24/30;
            return timeLong + "月前";

        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
            return sdf.format(startDate);
        }
    }

    /**
     * 获取两地距离
     * @param startLongitude
     * @param startLatitude
     * @param endLongitude
     * @param endLatitude
     * @return
     */
    public static String getLongDistance(double startLongitude, double startLatitude, double endLongitude, double endLatitude) {
        double ew1, ns1, ew2, ns2;
        double distance, temp;
        //角度转换为弧度
        ew1 = startLongitude * DEF_PI180;
        ns1 = startLatitude * DEF_PI180;
        ew2 = endLongitude * DEF_PI180;
        ns2 = endLatitude * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1-ew2);
        // 调整到[-1 1]范围内，避免溢出
        if (distance > 1.0) {
            distance = 1.0;
        } else if (distance < -1.0) {
            distance = -1.0;
        }
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);     //米
        //处理double类型的数据精确到小数点后两位
        temp = distance/1000;
        BigDecimal bigDecimal = new BigDecimal(temp);
        distance = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        if (distance < 1) {
            return "距离" + distance * 1000 + "米";
        } else {
            return "距离" + distance + "公里";
        }

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



























