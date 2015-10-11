package com.zdzyc.iptv.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.adapter.StringListAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 电视剧
 */
public class TeleplayFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SwipeDismissRecyclerViewTouchListener.DismissCallbacks {


    @Bind(R.id.teleplay_recycler_view)
    SuperRecyclerView teleplayRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private SparseItemRemoveAnimator mSparseAnimator;
    private StringListAdapter mAdapter;
    private Handler mHandler;
    
    public TeleplayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teleplay, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        teleplayRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<String> list = new ArrayList<>();
        mAdapter = new StringListAdapter(list);

        teleplayRecyclerView.setupSwipeToDismiss(this);
        mSparseAnimator = new SparseItemRemoveAnimator();
        teleplayRecyclerView.getRecyclerView().setItemAnimator(mSparseAnimator);
        mHandler = new Handler(Looper.getMainLooper());
        teleplayRecyclerView.setAdapter(mAdapter);
        mAdapter.addAll(new String[]{"More stuff", "More stuff", "More stuff"});

        teleplayRecyclerView.setRefreshListener(this);
        teleplayRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        teleplayRecyclerView.setupMoreListener(this, 1);
    }

    @Override
    public boolean canDismiss(int i) {
        return false;
    }

    @Override
    public void onDismiss(RecyclerView recyclerView, int[] ints) {

    }

    @Override
    public void onMoreAsked(int i, int i1, int i2) {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                mAdapter.add("More asked, more served");
            }
        }, 300);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                mAdapter.clear();
                mAdapter.addAll(new String[]{"More stuff", "More stuff", "More stuff"});
            }
        }, 2000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
