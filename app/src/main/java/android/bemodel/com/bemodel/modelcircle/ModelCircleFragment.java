package android.bemodel.com.bemodel.modelcircle;

import android.app.Activity;
import android.bemodel.com.bemodel.adapter.ModelCircleAdapter;
import android.bemodel.com.bemodel.base.BaseFragment;
import android.bemodel.com.bemodel.bean.ModelCircleInfo;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import rx.Subscriber;


public class ModelCircleFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.rv_model_Circle)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;


    private static final String TAG ="BeModel";
    private View view;
    private Context context;

    public List<ModelCircleInfo> modelCircleInfoList;
    private ModelCircleAdapter modelCircleAdapter;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_model_circle, container, false);
        this.context = inflater.getContext();

//        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshModelCicleInfo();
            }
        });

        return view;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_model_circle;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initView(Bundle savedInstanceState, View view) {
        //展示逻辑
        queryModelCircleInfoData();
        if (modelCircleInfoList != null) {
            modelCircleAdapter = new ModelCircleAdapter(context, modelCircleInfoList);
        } else {
            modelCircleAdapter = new ModelCircleAdapter(context, null);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(modelCircleAdapter);
    }

    //从服务器获取数据
    public void queryModelCircleInfoData() {
        BmobQuery<ModelCircleInfo> query = new BmobQuery<ModelCircleInfo>();

        query.setLimit(50);
        query.order("createdAt");
        //判断是否有缓存
        boolean isCache = query.hasCachedResult(ModelCircleInfo.class);
        if (isCache) {
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.findObjectsObservable(ModelCircleInfo.class)
                .subscribe(new Subscriber<List<ModelCircleInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        loge(throwable);
                    }

                    @Override
                    public void onNext(List<ModelCircleInfo> modelCircleInfos) {
                        modelCircleInfoList = modelCircleInfos;
                    }
                });

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
                    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
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

    public void loge(Throwable e) {
        Log.i(TAG,"===============================================================================");
        if(e instanceof BmobException){
            Log.e(TAG, "错误码："+((BmobException)e).getErrorCode()+",错误描述："+((BmobException)e).getMessage());
        }else{
            Log.e(TAG, "错误描述："+e.getMessage());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                queryModelCircleInfoData();
                break;
        }
    }


    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return 0;
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
