package com.zdzyc.iptv.fragment;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.activity.DetailedActivity;
import com.zdzyc.iptv.adapter.NewsAdapter;
import com.zdzyc.iptv.data.entity.Meizhi;
import com.zdzyc.iptv.data.entity.News;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 */
public class HotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SwipeDismissRecyclerViewTouchListener.DismissCallbacks {


    @Bind(R.id.hot_recycler_view)
    SuperRecyclerView hotRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
    private SparseItemRemoveAnimator mSparseAnimator;
    private CommonAdapter mAdapter;
    private Handler mHandler;

    ArrayList<Meizhi> mdata;

    public HotFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        hotRecyclerView.setLayoutManager(mLayoutManager);
        mdata = new ArrayList<Meizhi>();
        initData();
        mAdapter = new CommonAdapter<Meizhi>(this, R.layout.msg_item_list, mdata)
        {
            @Override
            public void convert(ViewHolder holder, Meizhi meizhi)
            {
                Glide.with()
                holder.setText(R.id.id_item_list_title, s);
            }
        };
        hotRecyclerView.setupSwipeToDismiss(this);
        mSparseAnimator = new SparseItemRemoveAnimator();
        hotRecyclerView.getRecyclerView().setItemAnimator(mSparseAnimator);
        mHandler = new Handler(Looper.getMainLooper());
        hotRecyclerView.setAdapter(mAdapter);

        hotRecyclerView.setRefreshListener(this);
        hotRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        hotRecyclerView.setupMoreListener(this, 1);

        mAdapter.setOnItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, News data) {
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                intent.putExtra("news",data);
                startActivity(intent);
            }
        });
    }

    private void initData() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
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
                mAdapter.addAll(mdata);
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                mAdapter.clear();
                initData();
                mAdapter.addAll(mdata);
            }
        }, 2000);
    }
}
