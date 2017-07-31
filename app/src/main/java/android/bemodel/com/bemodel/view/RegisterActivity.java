package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.BaseActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.http.bean.Init;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    EditText et_mobile_phone_number;
    EditText et_sms_code;
    EditText et_password;

    Button btn_get_smscode;
    Button btn_next_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_mobile_phone_number = (EditText)findViewById(R.id.et_telephone_number_register);
        et_sms_code = (EditText)findViewById(R.id.et_identifying_code_register);
        et_password = (EditText)findViewById(R.id.et_password_register);

        btn_get_smscode = (Button)findViewById(R.id.btn_get_sms_code);
        btn_next_step = (Button)findViewById(R.id.btn_next_step);

    }

    String mobilePhoneNumber;
    String smsCode;
    String password;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_sms_code:
                mobilePhoneNumber = et_mobile_phone_number.getText().toString().trim();


                break;

            case R.id.btn_next_step:
                mobilePhoneNumber = et_mobile_phone_number.getText().toString().trim();
                smsCode = et_sms_code.getText().toString().trim();
                password = et_password.getText().toString().trim();
                if (mobilePhoneNumber.equals("")) {
                    toast("请填写电话号码");
                    return;
                }
                if (smsCode.equals("")) {
                    toast("请填写验证码");
                    return;
                }
                if (password.equals("")) {
                    toast("请填写密码");
                    return;
                }
                BmobUser bmobUser = new BmobUser();
                bmobUser.setMobilePhoneNumber(mobilePhoneNumber);
                bmobUser.setPassword(password);
        }
    }
}
