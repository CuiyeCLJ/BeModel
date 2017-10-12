package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.bean.UserInfo;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register2Activity extends BaseActivity implements View.OnClickListener {

    private EditText et_nickname;

    private Switch sw_sex_select;

    private TextView tvTitleText;
    private Button btnLeft;
    private Button btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initViews(savedInstanceState);
    }


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        sw_sex_select = (Switch)findViewById(R.id.sw_sex_select_register2);
        et_nickname = (EditText)findViewById(R.id.et_nickname_register2);

        btnLeft = (Button)findViewById(R.id.left_btn);
        btnRight = (Button)findViewById(R.id.right_btn);
        tvTitleText = (TextView)findViewById(R.id.title_text);

        btnLeft.setText("上一步");
        btnRight.setText("完成");
        tvTitleText.setText("注册");
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

        UserInfo userInfo = (UserInfo)getIntent().getSerializableExtra("bmobUser_data");
        userInfo.setUsername(userName);
        userInfo.setSex(sex);

        addSubscription(userInfo.signUp(new SaveListener<UserInfo>() {
            @Override
            public void done(UserInfo userInfo, BmobException e) {
                if (e == null) {
                    toast("注册成功: " + userInfo.toString());
                } else {
                    loge(e);
                }
            }
        }));
    }

}
