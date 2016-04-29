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

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.activity.DetailedActivity;
import com.zdzyc.iptv.adapter.NewsAdapter;
import com.zdzyc.iptv.model.data.entity.News;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SwipeDismissRecyclerViewTouchListener.DismissCallbacks {


    @Bind(R.id.movie_recycler_view)
    SuperRecyclerView movieRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SparseItemRemoveAnimator mSparseAnimator;
    private NewsAdapter mAdapter;
    private Handler mHandler;

    ArrayList<News> mdata;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        movieRecyclerView.setLayoutManager(mLayoutManager);
        mdata = new ArrayList<News>();
        initData();
        mAdapter = new NewsAdapter(mdata);
        movieRecyclerView.setupSwipeToDismiss(this);
        mSparseAnimator = new SparseItemRemoveAnimator();
        movieRecyclerView.getRecyclerView().setItemAnimator(mSparseAnimator);
        mHandler = new Handler(Looper.getMainLooper());
        movieRecyclerView.setAdapter(mAdapter);

        movieRecyclerView.setRefreshListener(this);
        movieRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        movieRecyclerView.setupMoreListener(this, 1);

        mAdapter.setOnItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, News data) {
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                intent.putExtra("news", data);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        mdata.add(new News("侏罗纪", "导演：科林·特莱沃若 主演：克里斯·帕拉特，布莱丝·达拉斯·霍华德，尼克·罗宾森，泰·辛普金斯，黄荣亮 类型：动作，冒险，科幻",
                "http://sh.189.cn/cms/upfiles/Article/admin/201509/2015092915145927957.jpg", null, "20151025", "", "120"));
        mdata.add(new News("侏罗纪", "导演：科林·特莱沃若 主演：克里斯·帕拉特，布莱丝·达拉斯·霍华德，尼克·罗宾森，泰·辛普金斯，黄荣亮 类型：动作，冒险，科幻",
                "http://sh.189.cn/cms/upfiles/Article/admin/201509/2015092915145927957.jpg", null, "20151025", "", "120"));
        mdata.add(new News("侏罗纪", "导演：科林·特莱沃若 主演：克里斯·帕拉特，布莱丝·达拉斯·霍华德，尼克·罗宾森，泰·辛普金斯，黄荣亮 类型：动作，冒险，科幻",
                "http://sh.189.cn/cms/upfiles/Article/admin/201509/2015092915145927957.jpg", null, "20151025", "", "120"));
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
