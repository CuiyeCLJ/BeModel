package android.bemodel.com.bemodel.home;

import android.Manifest;
import android.app.Activity;
import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.adapter.ViewPagerAdapter;
import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.ui.messages.MessagesFragment;
import android.bemodel.com.bemodel.ui.modelcircle.ModelCircleFragment;
import android.bemodel.com.bemodel.ui.mycenter.PersonalCenterFragment;
import android.bemodel.com.bemodel.ui.uploadwork.UploadWorksFragment;
import android.bemodel.com.bemodel.util.ToastUtils;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;

import com.joker.annotation.PermissionsDenied;
import com.joker.annotation.PermissionsGranted;
import com.joker.annotation.PermissionsRequestSync;
import com.joker.api.Permissions4M;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

import static android.bemodel.com.bemodel.home.MainActivity.LOCATION_CODE;
import static android.bemodel.com.bemodel.home.MainActivity.PHONE_CODE;
import static android.bemodel.com.bemodel.home.MainActivity.STORAGE_CODE;

@PermissionsRequestSync(permission = {Manifest.permission.ACCESS_FINE_LOCATION,
Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
value = {LOCATION_CODE, PHONE_CODE, STORAGE_CODE})
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    public static final int LOCATION_CODE = 7;
    public static final int PHONE_CODE = 8;
    public static final int STORAGE_CODE = 9;

    @BindView(R.id.vp_main) ViewPager mViewPager;
    @BindView(R.id.tl_main) TabLayout tabLayout;

    private List<TabLayout.Tab> tabList;

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private ModelCircleFragment mModelCircleFragment;
    private MessagesFragment mMessagesFragment;
    private UploadWorksFragment mUploadWorksFragment;
    private PersonalCenterFragment mPersonalCenterFragment;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bmob.initialize(this, "af56c01af0a81b902b06a40b76af555a");
        BmobSMS.initialize(this, "af56c01af0a81b902b06a40b76af555a");

        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        initFragment(savedInstanceState);
        setViewPager();

        //同步申请多个权限
        Permissions4M.get(MainActivity.this).requestSync();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Permissions4M.onRequestPermissionsResult(MainActivity.this, requestCode, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionsGranted({LOCATION_CODE, PHONE_CODE, STORAGE_CODE})
    public void syncGranted(int code) {
        switch (code) {
            case LOCATION_CODE:
                ToastUtils.showLong("地理位置权限授权成功");
                Log.d(TAG, "syncGranted: 地理位置权限授权成功");
                break;
            case PHONE_CODE:
                ToastUtils.showLong("读取电话状态权限授权成功");
                Log.d(TAG, "syncGranted: 读取电话状态权限授权成功");
                break;
            case STORAGE_CODE:
                ToastUtils.showLong("读取SD卡权限授权成功");
                Log.d(TAG, "syncGranted: 读取SD卡权限授权成功");
                break;
            default:
                break;
        }
    }
    
    @PermissionsDenied({LOCATION_CODE, PHONE_CODE, STORAGE_CODE})
    public void syncDenied(int code) {
        switch (code) {
            case LOCATION_CODE:
                ToastUtils.showLong("地理位置权限授权失败");
                Log.d(TAG, "syncDenied: 地理位置权限授权失败");
                break;
            case PHONE_CODE:
                ToastUtils.showLong("读取电话状态权限授权失败");
                Log.d(TAG, "syncDenied: 读取电话状态权限授权失败");
                break;
            case STORAGE_CODE:
                ToastUtils.showLong("读取SD卡权限授权失败");
                Log.d(TAG, "syncDenied: 读取SD卡权限授权失败");
                break;
            default:
                break;
        }
    }

    @Override
    protected void initVariables() {}

    @Override
    protected void loadData() {}

    @Override
    protected void initViews() {

        tabList = new ArrayList<>();

        tabList.add(tabLayout.getTabAt(0));
        tabList.add(tabLayout.getTabAt(1));
        tabList.add(tabLayout.getTabAt(2));
        tabList.add(tabLayout.getTabAt(3));

        tabList.get(0).setIcon(R.drawable.homepage).setText("模特圈");
        tabList.get(1).setIcon(R.drawable.message).setText("消息");
        tabList.get(2).setIcon(R.drawable.upload).setText("上传作品");
        tabList.get(3).setIcon(R.drawable.people).setText("个人中心");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0:
//                        mTitleActivity.setTitle("模特圈");
//                        mTitleActivity.showLeftView(R.string.model_cicle_left_button, true);
//                        mTitleActivity.showRightView(R.string.upload_work_right_button, false);
//                        break;
//                    case 1:
//                        mTitleActivity.setTitle("消息");
//                        mTitleActivity.showLeftView(R.string.model_cicle_left_button, false);
//                        mTitleActivity.showRightView(R.string.upload_work_right_button, false);
//                        break;
//                    case 2:
//                        mTitleActivity.setTitle("上传作品");
//                        mTitleActivity.showLeftView(R.string.model_cicle_left_button, false);
//                        mTitleActivity.showRightView(R.string.upload_work_right_button, true);
//                        break;
//                    case 3:
//                        mTitleActivity.setTitle("个人中心");
//                        mTitleActivity.showLeftView(R.string.model_cicle_left_button, false);
//                        mTitleActivity.showRightView(R.string.upload_work_right_button, false);
//                        break;
//                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (mModelCircleFragment.isAdded()) {
            fragmentManager.putFragment(outState, "ModelCircleFragment", mModelCircleFragment);
        }
        if (mMessagesFragment.isAdded()) {
            fragmentManager.putFragment(outState, "MessagesFragment", mMessagesFragment);
        }
        if (mUploadWorksFragment.isAdded()) {
            fragmentManager.putFragment(outState, "UploadWorksFragment", mUploadWorksFragment);
        }
        if (mPersonalCenterFragment.isAdded()) {
            fragmentManager.putFragment(outState, "PersonalCenterFragment", mPersonalCenterFragment);
        }
    }

    /**
     * 初始化fragment的记忆状态
     * @param savedInstanceState
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (savedInstanceState != null) {
            mModelCircleFragment = (ModelCircleFragment)getSupportFragmentManager().getFragment(savedInstanceState, "ModelCircleFragment");
            mMessagesFragment = (MessagesFragment)getSupportFragmentManager().getFragment(savedInstanceState, "MessagesFragment");
            mUploadWorksFragment = (UploadWorksFragment)getSupportFragmentManager().getFragment(savedInstanceState, "UploadWorksFragment");
            mPersonalCenterFragment = (PersonalCenterFragment)getSupportFragmentManager().getFragment(savedInstanceState, "PersonalCenterFragment");
        } else {
            mModelCircleFragment = new ModelCircleFragment();
            mMessagesFragment = new MessagesFragment();
            mUploadWorksFragment = new UploadWorksFragment();
            mPersonalCenterFragment = new PersonalCenterFragment();
        }
        mFragmentList.add(mModelCircleFragment);
        mFragmentList.add(mMessagesFragment);
        mFragmentList.add(mUploadWorksFragment);
        mFragmentList.add(mPersonalCenterFragment);
    }

    /**
     * 入口
     * @param activity
     */
    public static void startAction(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
