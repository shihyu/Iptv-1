package com.zdzyc.iptv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zdzyc on 2015/9/20.
 */
public class DetailedAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment;
    String[] _titles;

    public DetailedAdapter(FragmentManager fm, String[] titles, List<Fragment> listFragment) {
        super(fm);
        _titles = titles;
        this.listFragment = listFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return _titles[position];
    }

    @Override
    public int getCount() {
        return _titles.length;
    }

    @Override
    public Fragment getItem(int position) {
        return  listFragment.get(position);
    }

}
