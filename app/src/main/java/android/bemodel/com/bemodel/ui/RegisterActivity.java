package android.bemodel.com.bemodel.ui;

import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.bean.UserInfo;
import android.content.Intent;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.et_telephone_number_register) EditText etMobilePhoneNumber;
    @BindView(R.id.et_identifying_code_register) EditText etSmsCode;
    @BindView(R.id.et_password_register) EditText etPassword;

    @BindView(R.id.btn_get_sms_code) Button btnGetmscode;

    @BindView(R.id.title_text) TextView tvTitleText;
    @BindView(R.id.left_btn) Button btnLeft;
    @BindView(R.id.right_btn) Button btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void initVariables() {
        
    }

    @Override
    protected void initViews() {
        btnLeft.setText("上一步");
        btnRight.setText("下一步");
        tvTitleText.setText("注册");

        btnGetmscode.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);

    }

    @Override
    protected void loadData() {


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_sms_code:
                mobilePhoneNumber = etMobilePhoneNumber.getText().toString().trim();
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

            case R.id.left_btn:
                finish();
                break;

            case R.id.right_btn:
                nextHandle();
                break;

            default:
                break;
        }
    }

    String mobilePhoneNumber;
    String smsCode;
    String password;

    private void nextHandle() {

        mobilePhoneNumber = etMobilePhoneNumber.getText().toString().trim();
        smsCode = etSmsCode.getText().toString().trim();
        password = etPassword.getText().toString().trim();
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
                    UserInfo user = new UserInfo();
                    user.setMobilePhoneNumber(mobilePhoneNumber);
                    user.setPassword(password);
                    Intent intent = new Intent(RegisterActivity.this, Register2Activity.class);
                    intent.putExtra("bmobUser_data", user);
                    startActivity(intent);
                } else {
                    Log.i("BeModel", "验证失败: code = " + e.getErrorCode() + ", msg = " + e.getLocalizedMessage());
                }
            }
        });

    }
}
