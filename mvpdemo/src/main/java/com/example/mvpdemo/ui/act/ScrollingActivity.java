package com.example.mvpdemo.ui.act;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.mvpdemo.R;
import com.google.android.material.appbar.AppBarLayout;

public class ScrollingActivity extends AppCompatActivity implements View.OnClickListener {

    private AppBarLayout app_bar;
    private LinearLayoutCompat ivScan;
    private LinearLayoutCompat ivPay;
    private LinearLayoutCompat ivCharge;
    private LinearLayoutCompat cardBag;
    private AppCompatImageView iv_scan_close;
    private AppCompatImageView iv_pay_close;
    private AppCompatImageView iv_charge_close;
    private AppCompatImageView cardBag_close;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        initView();
    }

    private void initView() {
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
        ivScan = findViewById(R.id.iv_scan);
        ivPay = findViewById(R.id.iv_pay);
        ivCharge = findViewById(R.id.iv_charge);
        cardBag = findViewById(R.id.cardBag);
        iv_scan_close = findViewById(R.id.iv_scan_close);
        iv_pay_close = findViewById(R.id.iv_pay_close);
        iv_charge_close = findViewById(R.id.iv_charge_close);
        cardBag_close = findViewById(R.id.cardBag_close);
        ivScan.setOnClickListener(this);
        ivPay.setOnClickListener(this);
        ivCharge.setOnClickListener(this);
        cardBag.setOnClickListener(this);
        iv_scan_close.setOnClickListener(this);
        iv_pay_close.setOnClickListener(this);
        iv_charge_close.setOnClickListener(this);
        cardBag_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan:
                Log.e("ScrollingActivity", "扫一扫");
                break;
            case R.id.iv_pay:
                Log.e("ScrollingActivity", "付钱");
                break;
            case R.id.iv_charge:
                Log.e("ScrollingActivity", "收钱");
                break;
            case R.id.cardBag:
                Log.e("ScrollingActivity", "卡包");
                break;
            case R.id.iv_scan_close:
                Log.e("ScrollingActivity", "扫一扫");
                break;
            case R.id.iv_pay_close:
                Log.e("ScrollingActivity", "付钱");
                break;
            case R.id.iv_charge_close:
                Log.e("ScrollingActivity", "收钱");
                break;
            case R.id.cardBag_close:
                Log.e("ScrollingActivity", "卡包");
                break;
            default:
                break;
        }
    }
}
