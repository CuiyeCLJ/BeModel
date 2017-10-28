package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.adapter.CommentAdapter;
import android.bemodel.com.bemodel.bean.CommentInfo;
import android.bemodel.com.bemodel.bean.ModelCircleInfo;
import android.bemodel.com.bemodel.bean.UserInfo;
import android.bemodel.com.bemodel.util.ToastUtils;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;

public class CommentActivity extends BaseActivity implements View.OnClickListener {

    private List<CommentInfo> commentInfoList = new ArrayList<>();
    private ModelCircleInfo modelCircleInfo;

    @BindView(R.id.title_text) TextView tvTitleText;
    @BindView(R.id.left_btn) Button btnLeft;
    @BindView(R.id.right_btn) Button btnRight;

    @BindView(R.id.et_review_content) EditText etReviewContent;
    @BindView(R.id.btn_issuance_review) Button btnIssuanceReview;

    private UserInfo user;

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
        user = BmobUser.getCurrentUser(mContext, UserInfo.class);
        modelCircleInfo = (ModelCircleInfo)getIntent().getSerializableExtra("modelCircleInfo_data");

    }

    @Override
    protected void initViews() {

        tvTitleText.setText("评论");
        btnLeft.setText("返回");
        btnRight.setVisibility(View.GONE);

        btnLeft.setOnClickListener(this);
        btnIssuanceReview.setOnClickListener(this);
    }

    @Override
    protected void loadData() {
        BmobQuery<CommentInfo> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("modelCircle", modelCircleInfo);
        bmobQuery.setLimit(50);
        bmobQuery.order("createdAt");
        boolean isCache = bmobQuery.hasCachedResult(mContext, CommentInfo.class);
        if (isCache) {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);
        } else {
            bmobQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        }
        bmobQuery.findObjects(this, new FindListener<CommentInfo>() {
            @Override
            public void onSuccess(List<CommentInfo> list) {
                commentInfoList = list;
            }

            @Override
            public void onError(int i, String s) {
                ToastUtils.showLong("请检查网络！");
            }
        });
//        bmobQuery.findObjectsObservable(CommentInfo.class)
//                .subscribe(new Subscriber<List<CommentInfo>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable throwable) {
//                        loge(throwable);
//                    }
//
//                    @Override
//                    public void onNext(List<CommentInfo> commentInfos) {
//                        commentInfoList = commentInfos;
//                    }
//                });
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
        commentInfo.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastUtils.showLong("评论成功");
                modelCircleInfo.setCommentsCount(modelCircleInfo.getCommentsCount() + 1);
            }

            @Override
            public void onFailure(int i, String s) {
                ToastUtils.showLong("评论失败");
            }
        });
//        commentInfo.save(mContext, new SaveListener<>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    toast("发布成功");
//                    //发布一条评论，使这条博文评论数 +1;
//
//                } else {
//                    toast("发布失败");
//                }
//            }
//        });
    }
}