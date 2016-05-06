package com.zdzyc.iptv.fragment;

import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SparseItemRemoveAnimator;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;
import com.squareup.picasso.Picasso;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.activity.DetailedActivity;
import com.zdzyc.iptv.adapter.NewsAdapter;
import com.zdzyc.iptv.api.GankApi;
import com.zdzyc.iptv.api.HttpMethods;
import com.zdzyc.iptv.data.MeizhiData;
import com.zdzyc.iptv.data.entity.Meizhi;
import com.zdzyc.iptv.data.entity.News;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.CommonAdapter;
import com.zhy.base.adapter.recyclerview.OnItemClickListener;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class HotFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener, SwipeDismissRecyclerViewTouchListener.DismissCallbacks {


    @Bind(R.id.hot_recycler_view)
    SuperRecyclerView hotRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;
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
        mLayoutManager = new LinearLayoutManager(getActivity());
        hotRecyclerView.setLayoutManager(mLayoutManager);
        mdata = new ArrayList<Meizhi>();
        mAdapter = new CommonAdapter<Meizhi>(getContext(), R.layout.msg_item_list, mdata) {
            @Override
            public void convert(ViewHolder holder, Meizhi meizhi) {
                Picasso.with(context).load(meizhi.getUrl()).into((ImageView) holder.getView(R.id.info_image));
                holder.setText(R.id.info_title, meizhi.getType());
                holder.setText(R.id.info_text, meizhi.getDesc());
                holder.setText(R.id.info_date,meizhi.getCreatedAt());
//                holder.setText(R.id.info_num,meizhi.get_id());
            }
        };
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup viewGroup, View view, Object o, int i) {
                Intent intent = new Intent(context, DetailedActivity.class);
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

        onRefresh();
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
                Toast.makeText(context, "Get meizi Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {

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
