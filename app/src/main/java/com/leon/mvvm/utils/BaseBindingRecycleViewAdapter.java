package com.leon.mvvm.utils;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leon.mvvm.BR;

import java.util.List;


/**
 * Created by leon on 2017/5/4.
 */

public class BaseBindingRecycleViewAdapter<T> extends RecyclerView.Adapter<BaseBindingRecycleViewAdapter<T>.BindingViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<T> mDataSource;
    private Object mViewModel;
    private int mItemLayoutId;

    public BaseBindingRecycleViewAdapter(Context context, int itemLayoutId, List<T> dataSource, Object viewModel) {
        mLayoutInflater = LayoutInflater.from(context);
        mViewModel = viewModel;
        mItemLayoutId = itemLayoutId;
        setDataSource(dataSource);
    }

    public BaseBindingRecycleViewAdapter<T> setDataSource(List<T> dataSource) {
        mDataSource = dataSource;
        return this;
    }

    public List<T> getDataSource() {
        return mDataSource;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater, mItemLayoutId, parent, false);
        return new BindingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BindingViewHolder holder, int position) {
        Object obj = mDataSource.get(position);
        holder.bind(obj, position);
    }

    @Override
    public int getItemCount() {
        return mDataSource == null ? 0 : mDataSource.size();
    }

    class BindingViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public BindingViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Object obj, int index) {
            binding.setVariable(BR.obj, obj);
            binding.setVariable(BR.viewModel, mViewModel);
            binding.setVariable(BR.index, index);
            binding.executePendingBindings();
        }
    }
}
