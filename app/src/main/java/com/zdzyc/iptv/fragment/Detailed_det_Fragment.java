package com.zdzyc.iptv.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdzyc.iptv.R;
import com.zdzyc.iptv.model.News;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 详细信息的简介类
 */
public class Detailed_det_Fragment extends Fragment {

    News news;
    @Bind(R.id.id_title)
    TextView idTitle;
    @Bind(R.id.id_details)
    TextView idDetails;
    @Bind(R.id.id_createAt)
    TextView idCreateAt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        news = (News) getActivity().getIntent().getParcelableExtra("news");
        View view = inflater.inflate(R.layout.fragment_detailed_det_, container, false);
        ButterKnife.bind(this, view);
        idTitle.setText(news.getTitle());
        idDetails.setText(news.getDetails());
        idCreateAt.setText(news.getCreatedAt());
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
