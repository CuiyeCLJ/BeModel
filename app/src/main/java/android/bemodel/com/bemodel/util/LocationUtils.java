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

        //地球半径
        double R = 6371;

        //两点间距离 km，如果想要米的话，结果*1000就可以了
        double d =  Math.acos(Math.sin(latitude1)*Math.sin(latitude2)+Math.cos(latitude1)*Math.cos(latitude2)*Math.cos(longitude2-longitude1))*R;

        return d;
    }

}
