package android.bemodel.com.bemodel.activity;

import android.bemodel.com.bemodel.R;
import android.bemodel.com.bemodel.adapter.ViewPagerAdapter;
import android.bemodel.com.bemodel.view.MessagesActivity;
import android.bemodel.com.bemodel.view.ModelCircleActivity;
import android.bemodel.com.bemodel.view.PersonalCenterActivity;
import android.bemodel.com.bemodel.view.UploadWorksActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<TabLayout.Tab> tabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        viewPager = (ViewPager)findViewById(R.id.vp_main);
        tabLayout = (TabLayout)findViewById(R.id.tl_main);

        tabList = new ArrayList<>();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new ModelCircleActivity());
        viewPagerAdapter.addFragment(new MessagesActivity());
        viewPagerAdapter.addFragment(new UploadWorksActivity());
        viewPagerAdapter.addFragment(new PersonalCenterActivity());

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

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
}
