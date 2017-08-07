package android.bemodel.com.bemodel.util;

import android.bemodel.com.bemodel.db.ModelCircleInfo;
import android.icu.text.RelativeDateTimeFormatter;
import android.provider.ContactsContract;
import android.widget.AbsListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.Pipe;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

/*
    public static ArrayList<ModelCircleInfo> getModelCircleContent(String jsonData) {

        ArrayList<ModelCircleInfo> modelCircleList = null;
        try {

            JSONArray jsonArray = new JSONArray(jsonData);

            for (int i = 0; i < 100; i++) {
                ModelCircleInfo modelCircleInfo = new ModelCircleInfo();
                JSONArray statusesList = jsonObject.getJSONArray("statuses");
                JSONObject modelCircleContent = statusesList.getJSONObject(i);
                JSONObject user = modelCircleContent.getJSONObject("user");
                String text = modelCircleContent.getString("text");
                String img = modelCircleContent.getString("pic_url");
                modelCircleInfo.setText(text);
                boolean haveImg = false;
                if (img != null) {
                    haveImg = true;
                    modelCircleInfo.setImageContext(img);
                }
                modelCircleInfo.setHaveImg(haveImg);
                String name = user.getString("screen_name");
                String userIcon = user.getString("avatar_large");
                String time = modelCircleContent.getString("created_at");
                Date startDate = new Date(time);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                time = simpleDateFormat.format(startDate);

                if (modelCircleList == null) {
                    modelCircleList = new ArrayList<>();
                }
                modelCircleInfo.setUserName(name);
                modelCircleInfo.setUserIcon(userIcon);
                modelCircleList.add(modelCircleInfo);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return modelCircleList;


    }
    */

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

}



























