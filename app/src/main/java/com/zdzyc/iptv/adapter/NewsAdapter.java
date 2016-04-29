package com.zdzyc.iptv.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zdzyc.iptv.R;
import com.zdzyc.iptv.data.entity.News;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public ArrayList<News> data;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public NewsAdapter(ArrayList<News> data) {
        this.data = data;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, News data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.msg_item_list, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news =  data.get(position);
        Picasso.with(context).load(news.getPicture()).into(holder.infoImage);
        holder.infoTitle.setText(news.getTitle());
        holder.infoText.setText(news.getDetails());
        holder.infoDate.setText(news.getCreatedAt());
        holder.infoNum.setText(news.getSupport());
        holder.itemView.setTag(news);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(News string) {
        insert(string, data.size());
    }

    public void insert(News string, int position) {
        data.add(position, string);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List strings) {
        int startIndex = data.size();
        data.addAll(strings);
        notifyItemRangeInserted(startIndex, strings.size());
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (News)v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.info_image)
        ImageView infoImage;
        @Bind(R.id.info_title)
        TextView infoTitle;
        @Bind(R.id.info_text)
        TextView infoText;
        @Bind(R.id.info_date)
        TextView infoDate;
        @Bind(R.id.info_num)
        TextView infoNum;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
