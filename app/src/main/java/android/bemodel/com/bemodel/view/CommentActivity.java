package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.BaseActivity;
import android.bemodel.com.bemodel.adapter.CommentAdapter;
import android.bemodel.com.bemodel.db.CommentInfo;
import android.bemodel.com.bemodel.db.ModelCircleInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import rx.Subscriber;

public class CommentActivity extends BaseActivity {

    private List<CommentInfo> commentInfoList = new ArrayList<>();
    private ModelCircleInfo modelCircleInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initVariables();
        loadData();
        CommentAdapter commentAdapter = new CommentAdapter(CommentActivity.this, R.layout.comment_item, commentInfoList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(commentAdapter);
    }

    @Override
    protected void initVariables() {
        modelCircleInfo = (ModelCircleInfo)getIntent().getSerializableExtra("modelCircleInfo_data");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void loadData() {
        BmobQuery<CommentInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("modelCircle", modelCircleInfo);
        bmobQuery.setLimit(50);
        bmobQuery.order("createdAt");
        boolean isCache = bmobQuery.hasCachedResult(CommentInfo.class);
        if (isCache) {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        bmobQuery.findObjectsObservable(CommentInfo.class)
                .subscribe(new Subscriber<List<CommentInfo>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        loge(throwable);
                    }

                    @Override
                    public void onNext(List<CommentInfo> commentInfos) {
                        commentInfoList = commentInfos;
                    }
                });
    }

}
