package android.bemodel.com.bemodel.util;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;

import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * Created by Administrator on 2017.08.10.
 */

public class LocationUtils {

    public LocationClient mLocationClient;

    public static final double EARTH_RADIUS = 6371; //地球半径 km

    /**
     * 计算两点之间距离
     * @param start
     * @param end
     * @return km
     */
    public static double getDistance(LatLng start, LatLng end){
        double latitude1 = (Math.PI/180)*start.latitude;
        double latitude2 = (Math.PI/180)*end.latitude;

        double longitude1 = (Math.PI/180)*start.longitude;
        double longitude2 = (Math.PI/180)*end.longitude;

        //两点间距离 km，如果想要米的话，结果*1000就可以了
        double d =  Math.acos(Math.sin(latitude1)*Math.sin(latitude2) +
                Math.cos(latitude1)*Math.cos(latitude2)*Math.cos(longitude2-longitude1))*EARTH_RADIUS;

        return d;
    }

    public static double getDistanceOther(LatLng ll1, LatLng ll2){
        return getDistance(ll1.latitude, ll1.longitude, ll2.latitude, ll2.longitude);
    }

    /**
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return  返回距离单位为米（m）
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s*1000;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 由两地距离获取描述性距离
     * @param distance  单位为km
     * @return
     */
    public static String getDescriptionDistance(double distance) {
        int d;
        String distanceStr = null;
        if (distance < 1) {
            d = (int)(distance * 1000);
            distanceStr = "距离" + d + "米";
        } else if (distance >= 1) {
            d = (int)distance;
            distanceStr = "距离" + d + "公里";
        }
        return distanceStr;
    }
    

}
