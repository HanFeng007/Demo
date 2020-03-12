package com.example.mvpdemo.ui.act;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvpdemo.R;
import com.google.android.material.appbar.AppBarLayout;

public class ScrollingActivity extends AppCompatActivity {

    private AppBarLayout app_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        app_bar = findViewById(R.id.app_bar);
        app_bar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //垂直方向偏移量
                int offset = Math.abs(verticalOffset);
                //最大偏移距离
                int scrollRange = appBarLayout.getTotalScrollRange();
                if (offset <= scrollRange / 2) {
                    findViewById(R.id.toolbar_open).setVisibility(View.VISIBLE);
                    findViewById(R.id.toolbar_close).setVisibility(View.GONE);
                    //根据偏移百分比 计算透明值
                    float scale = offset / (scrollRange / 2);
                    int alpha = (int) (255 * scale);
                    findViewById(R.id.bg_toolbar_open).setBackgroundColor(Color.argb(alpha, 25, 131, 209));
                }
                //当滑动超过一半，收缩状态下 toolbar 显示内容，根据收缩位置，改变透明值
                else {
                    findViewById(R.id.toolbar_open).setVisibility(View.GONE);
                    findViewById(R.id.toolbar_close).setVisibility(View.VISIBLE);
                    int scale = (scrollRange - offset) / (scrollRange / 2);
                    int alpha = 255 * scale;
                    findViewById(R.id.bg_toolbar_open).setBackgroundColor(Color.argb(alpha, 25, 131, 209));
                }
                //根据百分比计算扫一扫布局透明值
                int scale = offset / scrollRange;
                int alpha = 255 * scale;
                findViewById(R.id.bg_content).setBackgroundColor(Color.argb(alpha, 25, 131, 209));
            }
        });
    }
}
