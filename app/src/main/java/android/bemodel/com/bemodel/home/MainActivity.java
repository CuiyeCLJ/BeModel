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
import android.support.annotation.IdRes;
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
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.vp_main)
    ViewPager mViewPager;
    @BindView(R.id.bottom_bar)
    BottomBar mBottomBar;

    private List<Fragment> mFragmentList;
    private ModelCircleFragment mModelCircleFragment;
    private MessagesFragment mMessagesFragment;
    private UploadWorksFragment mUploadWorksFragment;
    private PersonalCenterFragment mPersonalCenterFragment;

    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Bmob.initialize(this, "af56c01af0a81b902b06a40b76af555a");
        BmobSMS.initialize(this, "af56c01af0a81b902b06a40b76af555a");

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

//        initFragment(savedInstanceState);
        initViewPager();
        setViewPager();

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                int position = mBottomBar.findPositionForTabWithId(tabId);
                mViewPager.setCurrentItem(position, false);
                mViewPagerAdapter.getItem(position);
            }
        });

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
    protected void initViews() {}

    private void initViewPager() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(ModelCircleFragment.newInstance());
        mFragmentList.add(MessagesFragment.newInstance());
        mFragmentList.add(UploadWorksFragment.newInstance());
        mFragmentList.add(PersonalCenterFragment.newInstance());

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mBottomBar.selectTabAtPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setViewPager() {
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());

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
