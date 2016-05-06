package com.zdzyc.iptv.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zdzyc.iptv.R;
import com.zdzyc.iptv.adapter.TobsAdapter;
import com.zdzyc.iptv.app.StatusBarCompat;
import com.zdzyc.iptv.fragment.CartoonFragment;
import com.zdzyc.iptv.fragment.EducationFragment;
import com.zdzyc.iptv.fragment.GameFragment;
import com.zdzyc.iptv.fragment.HotFragment;
import com.zdzyc.iptv.fragment.MovieFragment;
import com.zdzyc.iptv.fragment.TeleplayFragment;
import com.zdzyc.iptv.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.search_button)
    Button seachBtn;
    @Bind(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @Bind(R.id.pager)
    ViewPager pager;


    ActionBarDrawerToggle mDrawerToggle;

    List<Fragment> listFragment = new ArrayList<>();
    final String tobsTitle[] = {"妹子", "干货", "电影", "动漫", "电视剧", "教育"};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 标题的文字需在setSupportActionBar之前，不然会无效
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        StatusBarCompat.compat(this, R.color.primaryDark);
//        StatusBarCompat.compat(this, getResources().getColor(R.color.background_tab_pressed));
        mDrawerToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar,
                R.string.abc_action_bar_home_description,
                R.string.abc_action_bar_home_description_format);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        listFragment.add(new HotFragment());
        listFragment.add(new GameFragment());
        listFragment.add(new MovieFragment());
        listFragment.add(new CartoonFragment());
        listFragment.add(new TeleplayFragment());
        listFragment.add(new EducationFragment());
        pager.setAdapter(new TobsAdapter(getSupportFragmentManager(), tobsTitle, listFragment));
        tabs.setViewPager(pager);


    }


    @OnClick(R.id.search_button)
    public void search_button() {
        startActivity(new Intent(MainActivity.this, SeachActivity.class));
    }

    @OnClick({R.id.header,R.id.nav_user})
    void navUser() {
        startActivity(new Intent(MainActivity.this, UserActivity.class));
    }

    @OnClick(R.id.nav_messages)
    void navMessages() {
        startActivity(new Intent(MainActivity.this, MessagesActivity.class));
    }

    @OnClick(R.id.nav_attention)
    void navAttention() {

    }

    @OnClick(R.id.nav_friends)
    void navFriends() {

    }

    @OnClick(R.id.nav_clear)
    void navClear() {

    }

    @OnClick(R.id.nav_about)
    void navAbout() {

    }


}
