package com.leon.mvvm.ui.main.home;

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

public class HomeFragment extends BaseVMFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        HomeViewModel viewModel = new HomeViewModel(getContext(), this);
        ViewDataBinding binding = binding(inflater, container, R.layout.fragment_home, viewModel);

        viewModel.init(binding);
        return binding.getRoot();
    }
}
