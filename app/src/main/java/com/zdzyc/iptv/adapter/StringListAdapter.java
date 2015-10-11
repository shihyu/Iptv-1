package com.zdzyc.iptv.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zdzyc.iptv.R;

import java.util.ArrayList;
import java.util.Arrays;

public class StringListAdapter extends RecyclerView.Adapter<StringListAdapter.ViewHolder> implements View.OnClickListener {

    public ArrayList<String> data;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    public StringListAdapter(ArrayList<String> data) {
        this.data = data;
    }

    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , String data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public StringListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item_list, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StringListAdapter.ViewHolder holder, int position) {
        holder.textView.setText(data.get(position));
        holder.itemView.setTag(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(String string) {
        insert(string, data.size());
    }

    public void insert(String string, int position) {
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

    public void addAll(String[] strings) {
        int startIndex = data.size();
        data.addAll(startIndex, Arrays.asList(strings));
        notifyItemRangeInserted(startIndex, strings.length);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(String)v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.info_text);
        }
    }
}
