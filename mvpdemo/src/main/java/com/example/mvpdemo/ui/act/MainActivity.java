package com.example.mvpdemo.ui.act;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.mvpdemo.R;
import com.example.mvpdemo.ui.frag.FirstFragment;
import com.example.mvpdemo.ui.frag.SecondFragment;
import com.example.mvpdemo.ui.frag.ThirdFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private TabLayout tablayout;
    private ViewPager viewpager;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);
        fragments = new ArrayList<>();
        fragments.add(new FirstFragment());
        fragments.add(new SecondFragment());
        fragments.add(new ThirdFragment());

        viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        tablayout.setupWithViewPager(viewpager);
        //tablayout的数据源
        titles = new ArrayList<>();
        titles.add("Android");
        titles.add("iOS");
        titles.add("前端");
        for (int i = 0; i < titles.size(); i++) {
            tablayout.getTabAt(i).setText(titles.get(i));
        }

    }
}
