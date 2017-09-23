package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.adapter.CommentAdapter;
import android.bemodel.com.bemodel.db.CommentInfo;
import android.bemodel.com.bemodel.db.ModelCircleInfo;
import android.bemodel.com.bemodel.db.UserInfo;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;

public class CommentActivity extends BaseActivity implements View.OnClickListener {

    private List<CommentInfo> commentInfoList = new ArrayList<>();
    private ModelCircleInfo modelCircleInfo;

    private TextView tvTitleText;
    private Button btnLeft;
    private Button btnRight;

    private EditText etReviewContent;
    private Button btnIssuanceReview;

    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        initViews(savedInstanceState);
        initVariables();
        loadData();
        CommentAdapter commentAdapter = new CommentAdapter(CommentActivity.this, R.layout.comment_item, commentInfoList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(commentAdapter);
    }

    @Override
    protected void initVariables() {
        user = (UserInfo) BmobUser.getCurrentUser();
        modelCircleInfo = (ModelCircleInfo)getIntent().getSerializableExtra("modelCircleInfo_data");

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        tvTitleText = (TextView)findViewById(R.id.title_text);
        btnLeft = (Button)findViewById(R.id.left_btn);
        btnRight = (Button)findViewById(R.id.right_btn);

        etReviewContent = (EditText)findViewById(R.id.et_review_content);
        btnIssuanceReview = (Button)findViewById(R.id.btn_issuance_review);

        tvTitleText.setText("评论");
        btnLeft.setText("返回");
        btnRight.setVisibility(View.GONE);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();   //销毁当前活动
                break;

            case R.id.btn_issuance_review:
                postComment();
                break;

        }
    }

    private String reviewContent;

    /**
     * 发布评论
     */
    private void postComment() {
        reviewContent = etReviewContent.getText().toString().trim();
        if (reviewContent.equals("")) {
            toast("评论不能为空");
            return;
        }
        final CommentInfo commentInfo = new CommentInfo();
        commentInfo.setText(reviewContent);
        commentInfo.setReviewer(user);
        commentInfo.setModelCircle(modelCircleInfo);
        commentInfo.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    toast("发布成功");
                    //发布一条评论，使这条博文评论数 +1;
                    modelCircleInfo.setCommentsCount(modelCircleInfo.getCommentsCount() + 1);
                } else {
                    toast("发布失败");
                }
            }
        });
    }
}