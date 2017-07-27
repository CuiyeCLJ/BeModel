package android.bemodel.com.bemodel.view;

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class ModelCircleFragment extends Fragment {

    private View rootView;

    private RecyclerView recyclerView;
    public List<ModelCircleInfo> modelCircleInfoList;
    private Context context;
    private ModelCircleAdapter modelCircleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_model_circle, container, false);
        initView(rootView, savedInstanceState);
        return rootView;
    }

    protected void initView(View view, Bundle savedInstanceState) {
        //展示逻辑
        context = getContext();
        recyclerView = (RecyclerView)rootView.findViewById(R.id.rv_model_Circle);
        requestModelCircleInfo();
        if (modelCircleInfoList != null) {
            modelCircleAdapter = new ModelCircleAdapter(context, modelCircleInfoList);
        } else {
            modelCircleAdapter = new ModelCircleAdapter(context, null);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(modelCircleAdapter);

    }

    public void requestModelCircleInfo() {
        String url = "";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容
                final String responseData = response.body().string();

                modelCircleInfoList = Utility.getModelCircleContent(responseData);

            }
        });
    }


}
