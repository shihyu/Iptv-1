package com.zdzyc.iptv.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.andexert.library.RippleView;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.adapter.DetailedAdapter;
import com.zdzyc.iptv.fragment.Detailed_Evaluate_Fragment;
import com.zdzyc.iptv.fragment.Detailed_correlation_Fragment;
import com.zdzyc.iptv.fragment.Detailed_det_Fragment;
import com.zdzyc.iptv.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailedActivity extends AppCompatActivity {

    @Bind(R.id.id_stickynavlayout_indicator)
    PagerSlidingTabStrip idStickynavlayoutIndicator;
    @Bind(R.id.id_stickynavlayout_viewpager)
    ViewPager idStickynavlayoutViewpager;

    private String[] mTitles = new String[]{"简介", "评价", "相关"};
    List<Fragment> listFragment = new ArrayList<>();
    @Bind(R.id.more)
    RippleView rippleView;
    @OnClick(R.id.back_button)
    void back_button() {
        rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ButterKnife.bind(this);

        listFragment.add(new Detailed_det_Fragment());
        listFragment.add(new Detailed_Evaluate_Fragment());
        listFragment.add(new Detailed_correlation_Fragment());

        idStickynavlayoutViewpager.setAdapter(new DetailedAdapter(getSupportFragmentManager(), mTitles, listFragment));
        idStickynavlayoutIndicator.setTextSize(30);
        idStickynavlayoutIndicator.setViewPager(idStickynavlayoutViewpager);
//        idStickynavlayoutViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset,
//                                       int positionOffsetPixels) {
////                idStickynavlayoutIndicator.scroll(position, positionOffset);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }


}
