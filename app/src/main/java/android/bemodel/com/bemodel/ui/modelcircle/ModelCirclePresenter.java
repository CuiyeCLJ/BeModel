package android.bemodel.com.bemodel.ui.modelcircle;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.bean.ModelCircleInfo;
import android.provider.ContactsContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.listener.FindListener;

import static android.bemodel.com.bemodel.util.ToastUtils.showLong;

/**
 * Created by Lifu.Zheng on 2017.10.29.
 */

public class ModelCirclePresenter implements ModelCircleContract.Presenter<ModelCircleContract.View> {


    private ModelCircleContract.View mModelCircleView;

    @Override
    public void start() {

    }

    @Override
    public void updateStatus(boolean pullToRefresh) {
        mModelCircleView.showLoading(pullToRefresh);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    Thread.sleep(2000);
                    BmobQuery<ModelCircleInfo> query = new BmobQuery<ModelCircleInfo>();
//                    List<BmobQuery<ModelCircleInfo>> and = new ArrayList<BmobQuery<ModelCircleInfo>>();
//                    BmobQuery<ModelCircleInfo> q1 = new BmobQuery<ModelCircleInfo>();

                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentTime= simpleDateFormat.format(new Date());
                    Date date = null;
                    try {
                        date = simpleDateFormat.parse(currentTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    query.addWhereLessThanOrEqualTo("createdAt", new BmobDate(date));
                    query.findObjects(this, new FindListener<ModelCircleInfo>() {
                        @Override
                        public void onSuccess(List<ModelCircleInfo> list) {
                            mModelCircleView.setData(list);
                        }

                        @Override
                        public void onError(int i, String s) {
                            showLong(R.string.snap_load_fail);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


}
