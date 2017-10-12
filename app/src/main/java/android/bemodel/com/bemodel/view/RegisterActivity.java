package android.bemodel.com.bemodel.view;

import android.bemodel.com.bemodel.base.BaseActivity;
import android.content.Intent;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.BmobUser;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private EditText etMobilePhoneNumber;
    private EditText etSmsCode;
    private EditText etPassword;

    private Button btnGetmscode;

    private TextView tvTitleText;
    private Button btnLeft;
    private Button btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews(savedInstanceState);
    }

    @Override
    protected void initVariables() {
        
    }

    @Override
    protected void initViews() {

    }

    protected void initViews(Bundle savedInstanceState) {
        etMobilePhoneNumber = (EditText)findViewById(R.id.et_telephone_number_register);
        etSmsCode = (EditText)findViewById(R.id.et_identifying_code_register);
        etPassword = (EditText)findViewById(R.id.et_password_register);

        btnGetmscode = (Button)findViewById(R.id.btn_get_sms_code);

        btnLeft = (Button)findViewById(R.id.left_btn);
        btnRight = (Button)findViewById(R.id.right_btn);
        tvTitleText = (TextView)findViewById(R.id.title_text);

        btnLeft.setText("上一步");
        btnRight.setText("下一步");
        tvTitleText.setText("注册");

    }

    @Override
    protected void loadData() {

    }

    String mobilePhoneNumber;
    String smsCode;
    String password;

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
                break;

        }
    }
}
