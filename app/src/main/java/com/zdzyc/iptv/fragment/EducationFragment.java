package com.zdzyc.iptv.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zdzyc.iptv.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

/**
 * 教育
 */
public class EducationFragment extends Fragment {


    @Bind(R.id.tab)
    PagerBottomTabLayout tab;

    int[] testColors = {0xFF00796B,0xFF5B4947,0xFF607D8B,0xFFF57C00,0xFFF57C00};

    Controller controller;

    List<Fragment> mFragments;

    public EducationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_education, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mFragments = new ArrayList<>();

        mFragments.add(new EducationFragment_weather());
        mFragments.add(createFragment("B"));
        mFragments.add(createFragment("C"));
        mFragments.add(createFragment("D"));

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.push_up_in, R.anim.push_up_out);
        transaction.add(R.id.frameLayout, mFragments.get(0));
        transaction.commit();
        //构建导航栏,得到Controller进行后续控制
        controller = tab.builder()
                .addTabItem(android.R.drawable.ic_menu_week, "天气",testColors[0])
                .addTabItem(android.R.drawable.ic_menu_compass, "位置",testColors[1])
                .addTabItem(android.R.drawable.ic_menu_search, "搜索",testColors[2])
                .addTabItem(android.R.drawable.ic_menu_help, "帮助",testColors[3])
                .setMode(TabLayoutMode.HIDE_TEXT | TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();
        controller.addTabItemClickListener(listener);
    }

    private void initData() {
    }

    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag)
        {
            Log.i("asd", "onSelected:" + index + "   TAG: " + tag.toString());

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
            transaction.replace(R.id.frameLayout,mFragments.get(index));
            transaction.commit();
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd","onRepeatClick:"+index+"   TAG: "+tag.toString());
        }
    };

    private Fragment createFragment(String content)
    {
        EducationFragment_A fragment = new EducationFragment_A();
        Bundle bundle = new Bundle();
        bundle.putString("content",content);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
