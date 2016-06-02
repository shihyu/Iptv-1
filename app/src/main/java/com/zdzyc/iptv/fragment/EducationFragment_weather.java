package com.zdzyc.iptv.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.api.HttpMethods;
import com.zdzyc.iptv.data.MeizhiData;
import com.zdzyc.iptv.data.entity.Meizhi;
import com.zdzyc.iptv.util.FixedSpeedScroller;
import com.zdzyc.iptv.widget.CustomViewPager;
import com.zdzyc.iptv.widget.ZoomOutPageTransformer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by zhoudezheng on 16/5/15.
 */
public class EducationFragment_weather extends Fragment implements ViewPager.OnPageChangeListener {

    @Bind(R.id.vp)
    CustomViewPager viewPager;
    @Bind(R.id.ry_container)
    RelativeLayout ryContainer;
    @Bind(R.id.loading)
    LinearLayout loading;

    private Context mContext;

    private int[] mImgs = {R.mipmap.wzx, R.mipmap.wzx, R.mipmap.wzx,
            R.mipmap.wzx, R.mipmap.wzx};
    private int mPosition;
    private FixedSpeedScroller scroller;
    private List<Meizhi> mDatas = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_education_weather, container, false);
        ButterKnife.bind(this, view);

        initData();
        return view;
    }


    private void init() {


        /**
         * 将Viewpager所在容器的事件分发交给ViewPager
         */
        ryContainer.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View v, MotionEvent event) {
                                               return viewPager.dispatchTouchEvent(event);
                                           }
                                       }
        );
        ViewpagerAdapter mAdapter = new ViewpagerAdapter();
        viewPager.setAdapter(mAdapter);
        //设置缓存数为展示的数目
        viewPager.setOffscreenPageLimit(mImgs.length);
        viewPager.setPageMargin(2);
        //设置切换动画
        viewPager.setPageTransformer(true, new
                        ZoomOutPageTransformer()

        );
        viewPager.addOnPageChangeListener(this);

        setViewPagerSpeed(250);

    }


    private void initData() {

        Subscriber subscriber = new Subscriber<MeizhiData>() {
            @Override
            public void onCompleted() {
                loading.setVisibility(View.GONE);

            }

            @Override
            public void onError(Throwable e) {
                loading.setVisibility(View.GONE);

            }

            @Override
            public void onNext(MeizhiData meizhiData) {
                mDatas.clear();
                mDatas.addAll(meizhiData.results);
                init();
            }
        };
        HttpMethods.getInstance().getMeizhiData(subscriber, 1);
    }


    /**
     * 设置ViewPager切换速度
     *
     * @param duration
     */
    private void setViewPagerSpeed(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            scroller = new FixedSpeedScroller(getActivity(), new AccelerateInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(duration);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPosition = position;
    }

    @Override
    public void onPageSelected(int position) {
//当手指左滑速度大于2000时viewpager右滑（注意是item+2）
        if (viewPager.getSpeed() < -1800) {
            viewPager.setCurrentItem(mPosition + 2);
            viewPager.setSpeed(0);
        } else if (viewPager.getSpeed() > 1800 && mPosition > 0) {
//当手指右滑速度大于2000时viewpager左滑（注意item-1即可）
            viewPager.setCurrentItem(mPosition - 1);
            viewPager.setSpeed(0);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class ViewpagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final Meizhi info = mDatas.get(position);
            //设置一大堆演示用的数据，麻里麻烦~~
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.viewpager_layout, null);
            ImageView ivPortrait = (ImageView) view.findViewById(R.id.iv);
            TextView fengxiang = (TextView) view.findViewById(R.id.tv_fengxiang);
//            TextView fengli = (TextView) view.findViewById(R.id.tv_fengli);
//            TextView high = (TextView) view.findViewById(R.id.tv_high);
//            TextView low = (TextView) view.findViewById(R.id.tv_low);
//            TextView data = (TextView) view.findViewById(R.id.tv_date);
            Glide.with(getActivity()).load(info.getUrl()).into(ivPortrait);
            fengxiang.setText(info.getDesc());
//            fengli.setText(info.getCreatedAt());


            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return mImgs.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
