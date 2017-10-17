package android.bemodel.com.bemodel.mycenter;

import android.bemodel.com.bemodel.bean.UserInfo;
import android.bemodel.com.bemodel.view.LoginActivity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;

public class PersonalCenterFragment extends Fragment implements View.OnClickListener {

    private View view;

    private Context context;

    @BindView(R.id.ll_logging_status) LinearLayout llLoggingStatus;
    @BindView(R.id.rl_my_works) RelativeLayout rlMyWorks;
    @BindView(R.id.rl_invite_friends) RelativeLayout rlInviteFriends;
    @BindView(R.id.rl_feedback) RelativeLayout rlFeedback;
    @BindView(R.id.rl_gesture_cipher) RelativeLayout rlGestureCipher;

    @BindView(R.id.iv_user_profile_image) ImageView ivUserProfileImage;

    @BindView(R.id.tv_user_name) TextView tvUserName;

    @BindView(R.id.btn_login_or_logoff) Button btnLoginOrLogoff;

    private UserInfo user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal_center, container, false);
        this.context = inflater.getContext();
        initViews();
        loadData();
        return view;
    }

    private void initViews() {

    }

    protected void loadData() {
        user = (UserInfo) BmobUser.getCurrentUser();
        if (user != null) {
            tvUserName.setText(user.getUsername());
            btnLoginOrLogoff.setText("退出登录");
            btnLoginOrLogoff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    user.logOut();
                }
            });

        } else {
            tvUserName.setText("未登录");
            btnLoginOrLogoff.setText("登录");
            btnLoginOrLogoff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
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
