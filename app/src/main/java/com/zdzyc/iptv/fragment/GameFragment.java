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
 *
 */
public class GameFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SwipeDismissRecyclerViewTouchListener.DismissCallbacks {


    @Bind(R.id.game_recycler_view)
    SuperRecyclerView gameRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SparseItemRemoveAnimator mSparseAnimator;
    private StringListAdapter mAdapter;
    private Handler mHandler;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        gameRecyclerView.setLayoutManager(mLayoutManager);
        ArrayList<String> list = new ArrayList<>();
        mAdapter = new StringListAdapter(list);

        gameRecyclerView.setupSwipeToDismiss(this);
        mSparseAnimator = new SparseItemRemoveAnimator();
        gameRecyclerView.getRecyclerView().setItemAnimator(mSparseAnimator);
        mHandler = new Handler(Looper.getMainLooper());
        gameRecyclerView.setAdapter(mAdapter);
        mAdapter.addAll(new String[]{"More stuff", "More stuff", "More stuff"});

        gameRecyclerView.setRefreshListener(this);
        gameRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        gameRecyclerView.setupMoreListener(this, 1);
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
