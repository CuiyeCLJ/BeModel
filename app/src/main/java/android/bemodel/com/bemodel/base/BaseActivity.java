package android.bemodel.com.bemodel.base;

import android.bemodel.com.bemodel.CustomApplcation;
import android.bemodel.com.bemodel.ui.LoginActivity;
import android.bemodel.com.bemodel.widget.dialog.DialogTips;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.exception.BmobException;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017.07.31.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BeModel";

    private CompositeSubscription mCompositeSubscription;

    public Context mContext;
    private Unbinder mUnbinder;
    private Toast mToast;

    //初始化变量
    protected abstract void initVariables();
    //加载layout布局文件，初始化控件，为控件挂上事件方法
    protected abstract void initViews();
    //获取数据
    protected abstract void loadData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        this.initViews();
    }

    /**
     * 解决Subscription内存泄露问题
     * @param s
     */
    protected void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    public void toast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public static void log(String msg) {
        Log.i(TAG,"===============================================================================");
        Log.i(TAG, msg);
    }

    public static void loge(Throwable e) {
        Log.i(TAG,"===============================================================================");
        if(e instanceof BmobException){
            Log.e(TAG, "错误码："+((BmobException)e).getErrorCode()+",错误描述："+((BmobException)e).getMessage());
        }else{
            Log.e(TAG, "错误描述："+e.getMessage());
        }
    }

    /** 打Log
     * ShowLog
     * @return void
     * @throws
     */
    public void showLog(String msg){
        Log.i("life",msg);
    }

    public void showToast(final String text) {
        if (!TextUtils.isEmpty(text)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mToast == null) {
                        mToast = Toast.makeText(getApplicationContext(), text,
                                Toast.LENGTH_LONG);
                    } else {
                        mToast.setText(text);
                    }
                    mToast.show();
                }
            });

        }
    }

    public void showToast(final int resId) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(BaseActivity.this.getApplicationContext(), resId,
                            Toast.LENGTH_LONG);
                } else {
                    mToast.setText(resId);
                }
                mToast.show();
            }
        });
    }

    /** 显示下线的对话框
     * showOfflineDialog
     * @return void
     * @throws
     */
    public void showOfflineDialog(final Context context) {
        DialogTips dialog = new DialogTips(this,"您的账号已在其他设备上登录!", "重新登录");
        // 设置成功事件
        dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int userId) {
                CustomApplcation.getInstance().logout();
                startActivity(new Intent(context, LoginActivity.class));
                finish();
                dialogInterface.dismiss();
            }
        });
        // 显示确认对话框
        dialog.show();
        dialog = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
