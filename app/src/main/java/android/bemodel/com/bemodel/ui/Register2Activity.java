package android.bemodel.com.bemodel.ui;

import android.app.ProgressDialog;
import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.bean.UserInfo;
import android.bemodel.com.bemodel.home.MainActivity;
import android.bemodel.com.bemodel.util.NetworkUtils;
import android.content.Intent;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.v3.listener.SaveListener;

public class Register2Activity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_nickname_register2) EditText et_nickname;

    @BindView(R.id.sw_sex_select_register2) Switch sw_sex_select;

    @BindView(R.id.title_text) TextView tvTitleText;
    @BindView(R.id.left_btn) Button btnLeft;
    @BindView(R.id.right_btn) Button btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {

        btnLeft.setText("上一步");
        btnRight.setText("完成");
        tvTitleText.setText("注册");

        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    String userName;
    boolean sex;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_btn:
                finish();
                break;

            case R.id.right_btn:
                completeRegister();
                break;

        }

    }

    /**
     * 完成注册
     */
    private void completeRegister() {
        userName = et_nickname.getText().toString().trim();
        if (userName.equals("")) {
            toast("请填写你的昵称");
            return;
        }
        sw_sex_select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = true;
                } else {
                    sex = false;
                }
            }
        });

        boolean isNetConnected = NetworkUtils.isNetworkAvailable(this);
        if (!isNetConnected) {
            showToast(R.string.network_tips);
            return;
        }

        final ProgressDialog progress = new ProgressDialog(Register2Activity.this);
        progress.setMessage("正在注册...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        UserInfo user = (UserInfo)getIntent().getSerializableExtra("bmobUser_data");
        user.setUsername(userName);
        user.setSex(sex);

        user.signUp(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                progress.dismiss();
                toast("注册成功");

                Intent intent = new Intent(Register2Activity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onFailure(int i, String s) {
                toast("注册失败：" + s);
                progress.dismiss();
            }
        });

//        addSubscription(user.signUp(new SaveListener<UserInfo>() {
//            @Override
//            public void done(UserInfo userInfo, BmobException e) {
//                if (e == null) {
//                    toast("注册成功: " + userInfo.toString());
//                } else {
//                    loge(e);
//                }
//            }
//        }));
    }

}
