package android.bemodel.com.bemodel.util;

import android.bemodel.com.bemodel.db.ModelCircleInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017.07.25.
 */

public class Utility {

    public ArrayList<ModelCircleInfo> getModelCircleContent(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            for (int i = 0; i < 100; i++) {
                ModelCircleInfo modelCircleInfo = new ModelCircleInfo();
                JSONArray jsonArray = jsonObject.getJSONArray("statuses");
                JSONObject modelCircleContent = jsonArray.getJSONObject(i);
                String text = modelCircleContent.getString("text");



            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}



























