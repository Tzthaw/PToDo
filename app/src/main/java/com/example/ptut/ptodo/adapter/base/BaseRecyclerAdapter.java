package com.example.ptut.ptodo.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.example.ptut.ptodo.viewholder.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T extends BaseViewHolder, W>
        extends RecyclerView.Adapter<T> {

    protected LayoutInflater mLayoutInflater;

    protected List<W> mData;

    public BaseRecyclerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mData = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<W> newData) {
        mData = newData;
        notifyDataSetChanged();
    }

    public void appendNewDatas(List<W> newData) {
        mData.addAll(newData);
        notifyDataSetChanged();
    }

    public void appendNewData(W newData) {
        mData.add(newData);
        notifyDataSetChanged();
    }

    public W getItemAt(int position) {
        if (position < mData.size() - 1)
            return mData.get(position);

        return null;
    }

    public List<W> getItems() {
        if (mData == null)
            return new ArrayList<>();

        return mData;
    }

    public void removeData(W data) {
        mData.remove(data);
        notifyDataSetChanged();
    }

    public void addNewData(W data) {
        mData.add(data);
        notifyDataSetChanged();
    }

    public void clearData() {
        mData = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void removeNull(){
        mData.remove(mData.size()-1);
        notifyDataSetChanged();
    }
}
