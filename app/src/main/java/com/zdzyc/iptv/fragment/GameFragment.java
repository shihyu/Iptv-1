package com.zdzyc.iptv.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.squareup.picasso.Picasso;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.activity.WebViewActivity;
import com.zdzyc.iptv.api.HttpMethods;
import com.zdzyc.iptv.data.MeizhiWithGankData;
import com.zdzyc.iptv.data.entity.Gank;
import com.zdzyc.iptv.data.entity.MeizhiWithGank;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 *
 */
public class GameFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SwipeDismissRecyclerViewTouchListener.DismissCallbacks {


    @Bind(R.id.game_recycler_view)
    SuperRecyclerView gameRecyclerView;
    @Bind(R.id.loading)
    LinearLayout loading;

    private RecyclerView.LayoutManager mLayoutManager;
    private SparseItemRemoveAnimator mSparseAnimator;
    private CommonAdapter mAdapter;
    private int pageSize;
    ArrayList<MeizhiWithGank> mdata;

    private Context context;

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
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
        mdata = new ArrayList<MeizhiWithGank>();
        mAdapter = new CommonAdapter<MeizhiWithGank>(getContext(), R.layout.msg_item_list, mdata) {
            @Override
            public void convert(ViewHolder holder, MeizhiWithGank meizhiWithGank) {
                Gank gank = meizhiWithGank.getGank();
                Picasso.with(context).load(meizhiWithGank.getImgUrl()).into((ImageView) holder.getView(R.id.info_image));
                holder.setText(R.id.info_title, gank.getType());
                holder.setText(R.id.info_text, gank.getDesc());
                holder.setText(R.id.info_date, gank.getCreatedAt());
//                holder.setText(R.id.info_num,meizhi.get_id());
            }
        };
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                Intent intent = new Intent(context, WebViewActivity.class);
                MeizhiWithGank meizhiWithGank = (MeizhiWithGank) o;
                intent.putExtra("news", meizhiWithGank.getGank());
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(ViewGroup viewGroup, View view, Object o, int i) {
                return false;
            }
        });
        gameRecyclerView.setupSwipeToDismiss(this);
        mSparseAnimator = new SparseItemRemoveAnimator();
        gameRecyclerView.getRecyclerView().setItemAnimator(mSparseAnimator);
        gameRecyclerView.setAdapter(mAdapter);
        gameRecyclerView.setRefreshListener(this);
        gameRecyclerView.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        gameRecyclerView.setupMoreListener(this, 1);
        loading.setVisibility(View.VISIBLE);
        initData(true);
    }

    private void initData(final boolean isRefresh) {
        if (isRefresh) {
            pageSize = 1;
        } else {
            pageSize++;
        }

        Subscriber subscriber = new Subscriber<MeizhiWithGankData>() {
            @Override
            public void onCompleted() {
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                loading.setVisibility(View.GONE);
            }

            @Override
            public void onNext(MeizhiWithGankData meizhiWithGankData) {
                if (isRefresh)
                    mdata.clear();
                mdata.addAll(meizhiWithGankData.data);
                mAdapter.notifyDataSetChanged();
            }
        };
        HttpMethods.getInstance().getGankData(subscriber, pageSize);


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
