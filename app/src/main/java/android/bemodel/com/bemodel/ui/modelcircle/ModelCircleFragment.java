package android.bemodel.com.bemodel.ui.modelcircle;

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
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.b.V;
import cn.bmob.v3.listener.FindListener;
import rx.Subscriber;

import static android.bemodel.com.bemodel.base.BaseActivity.log;
import static android.bemodel.com.bemodel.base.BaseActivity.loge;
import static android.bemodel.com.bemodel.util.ToastUtils.showLong;
import static android.bemodel.com.bemodel.util.ToastUtils.showShort;
import static com.baidu.location.d.j.v;


public class ModelCircleFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.rv_model_Circle)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    private static final String TAG ="BeModel";

    private Context context;

    public List<ModelCircleInfo> modelCircleInfoList;
    private ModelCircleAdapter modelCircleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.context = inflater.getContext();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int bindLayout() {
        return R.layout.fragment_model_circle;
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void initView(final Bundle savedInstanceState, View view) {

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshModelCicleInfo(savedInstanceState);
            }
        });

        //展示逻辑
        loadData();
        if (modelCircleInfoList != null) {
            modelCircleAdapter = new ModelCircleAdapter(context, modelCircleInfoList, recyclerView);
        } else {
            modelCircleAdapter = new ModelCircleAdapter(context, null, recyclerView);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(modelCircleAdapter);
    }

    //从服务器获取数据
    public void loadData() {
        BmobQuery<ModelCircleInfo> query = new BmobQuery<ModelCircleInfo>();

        query.setLimit(50);
        query.order("createdAt");
        //判断是否有缓存
        boolean isCache = query.hasCachedResult(context, ModelCircleInfo.class);
        if (isCache) {
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }

        query.findObjects(context, new FindListener<ModelCircleInfo>() {
            @Override
            public void onSuccess(List<ModelCircleInfo> list) {
                modelCircleInfoList = list;
            }

            @Override
            public void onError(int i, String s) {
                showLong(R.string.snap_load_fail);
            }
        });

        /*
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

         */

    }

    /*
    private void loadError(Throwable throwable) {
        throwable.printStackTrace();
        Snackbar.make(recyclerView, R.string.snap_load_fail, Snackbar.LENGTH_LONG)
                .setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestDataRefresh();
                    }
                }).show();
    }
     */

    private void requestDataRefresh() {
        modelCircleInfoList.clear();
        loadData();
    }

    //下拉刷新
    private void refreshModelCicleInfo(final Bundle savedInstanceState) {
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
                        requestDataRefresh();
                        modelCircleAdapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                loadData();
                break;
        }
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {

    }
}
