package android.bemodel.com.bemodel.view;

import android.app.Activity;
import android.bemodel.com.bemodel.adapter.ModelCircleAdapter;
import android.bemodel.com.bemodel.db.ModelCircleInfo;
import android.bemodel.com.bemodel.util.HttpUtil;
import android.bemodel.com.bemodel.util.Utility;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ModelCircleFragment extends Fragment {

    private View rootView;

    private RecyclerView recyclerView;
    public List<ModelCircleInfo> modelCircleInfoList;
    private Context context;
    private ModelCircleAdapter modelCircleAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_model_circle, container, false);

        initView();

        swipeRefresh = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh);
//        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshModelCicleInfo();
            }
        });

        return rootView;
    }

    protected void initView() {
        //展示逻辑
        context = getContext();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.rv_model_Circle);
        queryModelCircleInfoData();
        if (modelCircleInfoList != null) {
            modelCircleAdapter = new ModelCircleAdapter(context, modelCircleInfoList);
        } else {
            modelCircleAdapter = new ModelCircleAdapter(context, null);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(modelCircleAdapter);

    }

    public void queryModelCircleInfoData() {
        BmobQuery<ModelCircleInfo> query = new BmobQuery<ModelCircleInfo>();

        query.setLimit(50);



//        String url = "";
//        HttpUtil.sendOkHttpRequest(url, new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                //得到服务器返回的具体内容
//                final String responseData = response.body().string();
//
//                modelCircleInfoList = Utility.getModelCircleContent(responseData);
//
//            }
//        });
    }
    //下拉刷新
    private void refreshModelCicleInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initView();
                        modelCircleAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

}
