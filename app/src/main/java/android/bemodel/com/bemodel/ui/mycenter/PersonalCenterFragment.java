package android.bemodel.com.bemodel.ui.mycenter;

import android.bemodel.com.bemodel.bean.UserInfo;
import android.bemodel.com.bemodel.util.image.ImageLoader;
import android.bemodel.com.bemodel.ui.LoginActivity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalCenterFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.ll_logging_status)
    LinearLayout llLoggingStatus;
    @BindView(R.id.rl_my_works)
    RelativeLayout rlMyWorks;
    @BindView(R.id.rl_invite_friends)
    RelativeLayout rlInviteFriends;
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.rl_gesture_cipher)
    RelativeLayout rlGestureCipher;

    @BindView(R.id.iv_user_profile_image)
    CircleImageView ivUserProfileImage;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.btn_login_or_logoff)
    Button btnLoginOrLogoff;

    @BindView(R.id.left_btn) Button btnLeft;
    @BindView(R.id.right_btn) Button btnRight;
    @BindView(R.id.title_text) TextView tvTitle;

    private View view;
    private Context mContext;
    private UserInfo user;

    private ImageLoader imageLoader;

    public static PersonalCenterFragment newInstance() {

        Bundle args = new Bundle();

        PersonalCenterFragment fragment = new PersonalCenterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        this.mContext = inflater.getContext();
        imageLoader = new ImageLoader();
        initViews();
        loadData();
        return view;
    }

    private void initViews() {

        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.GONE);
        tvTitle.setText(R.string.PersonalCenterFragment_title_text);
        rlMyWorks.setOnClickListener(this);
        rlInviteFriends.setOnClickListener(this);
        rlFeedback.setOnClickListener(this);
        rlGestureCipher.setOnClickListener(this);

    }

    protected void loadData() {
        user = BmobUser.getCurrentUser(mContext, UserInfo.class);
        if (user != null) {
            tvUserName.setText(user.getUsername());
            //加载个人头像
            imageLoader.dispalyImage(user.getAvatar(), ivUserProfileImage);
            btnLoginOrLogoff.setText("退出登录");
            btnLoginOrLogoff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BmobUser.logOut(mContext);
                    BmobUser currentUser = BmobUser.getCurrentUser(mContext);
                }
            });

        } else {
            tvUserName.setText("未登录");
            btnLoginOrLogoff.setText("登录");
            btnLoginOrLogoff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.startAction(mContext);
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_my_works:

            case R.id.rl_invite_friends:

            case R.id.rl_feedback:

            case R.id.rl_gesture_cipher:

        }
    }
}
