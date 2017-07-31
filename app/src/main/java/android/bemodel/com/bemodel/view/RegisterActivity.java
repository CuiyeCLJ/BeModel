package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.BaseActivity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
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
                BmobSMS.requestSMSCode(this, mobilePhoneNumber, "BeModel验证码", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {    //验证码发送成功
                            Log.i("BeModel", "短信id: " + integer);
                            //用于查询本次短信发送详情
                        }
                    }
                });
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
                BmobSMS.verifySmsCode(this, mobilePhoneNumber, smsCode, new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Log.i("BeModel", "验证通过");
                            BmobUser bmobUser = new BmobUser();
                            bmobUser.setMobilePhoneNumber(mobilePhoneNumber);
                            bmobUser.setPassword(password);
                            Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
                            intent.putExtra("bmobUser_data", bmobUser);
                            startActivity(intent);
                        } else {
                            Log.i("BeModel", "验证失败: code = " + e.getErrorCode() + ", msg = " + e.getLocalizedMessage());
                        }
                    }
                });

        }
    }
}
