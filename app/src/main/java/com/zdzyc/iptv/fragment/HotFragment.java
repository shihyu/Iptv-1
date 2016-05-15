package com.zdzyc.iptv.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.activity.MeizhiDetailedActivity;
import com.zdzyc.iptv.api.HttpMethods;
import com.zdzyc.iptv.data.MeizhiData;
import com.zdzyc.iptv.data.entity.Meizhi;
import com.zdzyc.iptv.util.ToastUtils;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 *
 */
public class HotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SwipeDismissRecyclerViewTouchListener.DismissCallbacks {


    @Bind(R.id.hot_recycler_view)
    SuperRecyclerView hotRecyclerView;
    @Bind(R.id.loading)
    LinearLayout loading;

    private SparseItemRemoveAnimator mSparseAnimator;
    private CommonAdapter mAdapter;
    private int pageSize;
    ArrayList<Meizhi> mdata;

    private Context context;

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
        context = getContext();
        return view;
    }

    private void initView() {
        hotRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mdata = new ArrayList<Meizhi>();
        mAdapter = new CommonAdapter<Meizhi>(getContext(), R.layout.hot_item_list, mdata) {
            @Override
            public void convert(ViewHolder holder, Meizhi meizhi) {
                ImageView imageView = (ImageView) holder.getView(R.id.info_image);
                Random random = new Random();
                int height = 250+random.nextInt(201);
                LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
                imageView.setLayoutParams(mParams);
                Glide.with(context).load(meizhi.getUrl()).into(imageView);
//                Picasso.with(context).load(meizhi.getUrl()).into(imageView);
                holder.setText(R.id.info_title, meizhi.getDesc());
            }
        };
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                Intent intent = new Intent(context, MeizhiDetailedActivity.class);
                intent.putExtra("news", (Meizhi) o);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });
        hotRecyclerView.setupSwipeToDismiss(this);
        mSparseAnimator = new SparseItemRemoveAnimator();
        hotRecyclerView.getRecyclerView().setItemAnimator(mSparseAnimator);
        hotRecyclerView.setAdapter(mAdapter);
        hotRecyclerView.setRefreshListener(this);
        hotRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        hotRecyclerView.setupMoreListener(this, 1);

        loading.setVisibility(View.VISIBLE);
        initData(true);

    }

    private void initData(final boolean isRefresh) {
        if (isRefresh) {
            pageSize = 1;
        } else {
            pageSize++;
        }

        Subscriber subscriber = new Subscriber<MeizhiData>() {
            @Override
            public void onCompleted() {
                loading.setVisibility(View.GONE);
                hotRecyclerView.hideProgress();
                hotRecyclerView.hideMoreProgress();
                hotRecyclerView.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                loading.setVisibility(View.GONE);
                hotRecyclerView.hideProgress();
                hotRecyclerView.hideMoreProgress();
                hotRecyclerView.setRefreshing(false);

                ToastUtils.showLong(""+e.getMessage());
            }

            @Override
            public void onNext(MeizhiData meizhiData) {
                if (isRefresh)
                    mdata.clear();
                mdata.addAll(meizhiData.results);
                mAdapter.notifyDataSetChanged();
            }
        };
        HttpMethods.getInstance().getMeizhiData(subscriber, pageSize);

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
        initData(false);
    }

    @Override
    public void onRefresh() {
        initData(true);
    }
}
