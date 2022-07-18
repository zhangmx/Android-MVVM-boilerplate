package com.leon.mvvm.ui.main.dashboard;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
//import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leon.mvvm.R;
import com.leon.mvvm.ui.base.mvvm.BaseVMFragment;

/**
 * Created by leon on 2017/6/20.
 */

public class DashboardFragment extends BaseVMFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DashboardViewModel viewModel = new DashboardViewModel(getContext(), this);
        ViewDataBinding binding = binding(inflater, container, R.layout.fragment_dashboard, viewModel);
        return binding.getRoot();
    }
}
