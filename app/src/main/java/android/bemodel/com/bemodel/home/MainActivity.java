package android.bemodel.com.bemodel.home;

import android.app.Activity;
import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.adapter.ViewPagerAdapter;
import android.bemodel.com.bemodel.base.BaseActivity;
import android.bemodel.com.bemodel.messages.MessagesFragment;
import android.bemodel.com.bemodel.modelcircle.ModelCircleFragment;
import android.bemodel.com.bemodel.mycenter.PersonalCenterFragment;
import android.bemodel.com.bemodel.uploadartwork.UploadWorksFragment;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main) ViewPager mViewPager;
    @BindView(R.id.tl_main) TabLayout tabLayout;

//    private ViewPager mViewPager;
//    private TabLayout tabLayout;
//    mViewPager = (ViewPager)findViewById(R.id.vp_main);
//    tabLayout = (TabLayout)findViewById(R.id.tl_main);

    private List<TabLayout.Tab> tabList;

    private ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
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

        tabList.get(0).setIcon(R.drawable.home).setText("模特圈");
        tabList.get(1).setIcon(R.drawable.messages).setText("消息");
        tabList.get(2).setIcon(R.drawable.uploadwork).setText("上传作品");
        tabList.get(3).setIcon(R.drawable.personalcenter).setText("个人中心");

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

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
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
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
