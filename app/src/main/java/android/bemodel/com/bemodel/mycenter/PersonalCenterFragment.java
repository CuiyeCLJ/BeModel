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

import cn.bmob.v3.BmobUser;

public class PersonalCenterFragment extends Fragment implements View.OnClickListener {

    private View view;

    private Context context;

    private LinearLayout llLoggingStatus;
    private RelativeLayout rlMyWorks;
    private RelativeLayout rlInviteFriends;
    private RelativeLayout rlFeedback;
    private RelativeLayout rlGestureCipher;

    private ImageView ivUserProfileImage;

    private TextView tvUserName;

    private Button btnLoginOrLogoff;

    private TextView tvTitleText;
    private Button btnLeft;
    private Button btnRight;

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

        llLoggingStatus = (LinearLayout)view.findViewById(R.id.ll_logging_status);
        ivUserProfileImage = (ImageView)view.findViewById(R.id.iv_user_profile_image);
        tvUserName = (TextView)view.findViewById(R.id.tv_user_name);

        rlMyWorks = (RelativeLayout)view.findViewById(R.id.rl_my_works);
        rlInviteFriends = (RelativeLayout)view.findViewById(R.id.rl_invite_friends);
        rlFeedback = (RelativeLayout)view.findViewById(R.id.rl_feedback);
        rlGestureCipher = (RelativeLayout)view.findViewById(R.id.rl_gesture_cipher);

        btnLoginOrLogoff = (Button)view.findViewById(R.id.btn_login_or_logoff);

        tvTitleText = (TextView)view.findViewById(R.id.title_text);
        btnLeft = (Button)view.findViewById(R.id.left_btn);
        btnRight = (Button)view.findViewById(R.id.right_btn);


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
