package android.bemodel.com.bemodel.view;

import android.app.ProgressDialog;
import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.bean.UserInfo;
import android.bemodel.com.bemodel.home.MainActivity;
import android.bemodel.com.bemodel.util.NetworkUtils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.bemodel.com.bemodel.R;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.TextView;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;

public class LoginActivity extends BaseActivity implements OnClickListener {

    @BindView(R.id.et_phone_number_login) EditText etAccount;
    @BindView(R.id.et_password_login) EditText etPassword;

    @BindView(R.id.bt_sign_in) Button btn_login;
    @BindView(R.id.bt_register) Button btn_register;

    @BindView(R.id.title_text) TextView tvTitleText;
    @BindView(R.id.left_btn) Button btnLeft;
    @BindView(R.id.right_btn) Button btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews() {

        tvTitleText.setText("登录");
        btnLeft.setText("取消");
        btnRight.setVisibility(View.GONE);

//        btn_login.setOnClickListener(this);
//        btn_register.setOnClickListener(this);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_sign_in:
                boolean isNetConnected = NetworkUtils.isNetworkAvailable(this);
                if (!isNetConnected) {  //检查网络是否可用
                    showToast(R.string.network_tips);
                    return;
                }
                loginHandle();
                break;

            case R.id.bt_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;

            case R.id.left_btn:
                finish();
                break;

        }
    }

    String account;
    String password;

    private void loginHandle() {
        account = etAccount.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (account.equals("")) {
            toast("请填写你的电话号码");
            return;
        }
        if (password.equals("")) {
            toast("请填写你的密码");
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("正在登陆...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

//        final BmobUser bmobUser = new BmobUser();
//        bmobUser.setMobilePhoneNumber(account);
//        bmobUser.setPassword(password);

        final UserInfo user = new UserInfo();
        user.setMobilePhoneNumber(account);
        user.setPassword(password);

        user.login(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                toast(user.getUsername() + "登陆成功");
                getCurrentUser();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int i, String s) {
                toast("登录失败:" + s);
                progressDialog.dismiss();
            }
        });

        /*
        //v3.5.0开始新增加的rx风格的Api
        bmobUser.loginObservable(BmobUser.class).subscribe(new Subscriber<BmobUser>() {
            @Override
            public void onCompleted() {
                log("----onCompleted----");
            }

            @Override
            public void onError(Throwable throwable) {
                loge(new BmobException(throwable));
            }

            @Override
            public void onNext(BmobUser bmobUser) {
                toast(bmobUser.getUsername() + "登陆成功");
                getCurrentUser();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
         */

    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    //获取本地用户
    private void getCurrentUser() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
