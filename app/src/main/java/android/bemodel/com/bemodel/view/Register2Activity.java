package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.BaseActivity;
import android.bemodel.com.bemodel.db.UserInfo;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class Register2Activity extends BaseActivity implements View.OnClickListener {

    EditText et_nickname;

    Switch sw_sex_select;

    Button btn_complete_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        initView();
    }

    private void initView() {
        sw_sex_select = (Switch)findViewById(R.id.sw_sex_select_register2);
        et_nickname = (EditText)findViewById(R.id.et_nickname_register2);
        btn_complete_register = (Button)findViewById(R.id.btn_complete_register);

    }

    String userName;
    boolean sex;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_complete_register:
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
}
