package com.leon.mvvm.ui.main.home;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.databinding.ViewDataBinding;
import androidx.core.content.ContextCompat;
//import androidx.appcompat.widget.LinearLayoutManager;
//import androidx.appcompat.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Toast;

import com.github.qing.itemdecoration.LinearDividerItemDecoration;
import com.leon.mvvm.R;
import com.leon.mvvm.data.remote.NetworkObserver;
import com.leon.mvvm.ui.main.notification.model.RxBusTestEvent;
import com.leon.mvvm.utils.RxBus;
import com.leon.mvvm.utils.Tools;
import com.leon.mvvm.databinding.FragmentHomeBinding;
import com.leon.mvvm.ui.base.BaseFragment;
import com.leon.mvvm.ui.base.mvvm.BaseViewModel;
import com.leon.mvvm.ui.main.home.model.HomeTestBean;
import com.leon.mvvm.utils.BaseBindingRecycleViewAdapter;

import java.util.List;

/**
 * Created by leon on 2017/6/20.
 */

public class HomeViewModel extends BaseViewModel {
    public static final String TYPE_HOUSE_KEEPER_REQ_REPAIR = "repair";
    public static final String TYPE_HOUSE_KEEPER_REQ_HELP = "help";

    private FragmentHomeBinding mBinding;
    private RecyclerView mRecyclerView;
    private BaseBindingRecycleViewAdapter<HomeTestBean> mAdapter;
    private List<HomeTestBean> mList;

    public HomeViewModel(Context context, BaseFragment fragment) {
        super(context, fragment);
    }

    @SuppressLint("CheckResult")
    void init(ViewDataBinding binding) {
        mBinding = (FragmentHomeBinding) binding;
        mRecyclerView = mBinding.list;

        mDataManager.getTestList()
                .compose(mFragment.bindToLifecycle())
                .subscribe(new NetworkObserver<List<HomeTestBean>>(mActivity) {
                    @Override
                    protected void onHandleSuccess(List<HomeTestBean> homeTestBeen) {
                        mList = homeTestBeen;
                        initList();
                    }
                });

        RxBus.getInstance().filteredObservable(RxBusTestEvent.class)
                .compose(mFragment.bindToLifecycle())
                .subscribe(rxBusTestEvent -> {
                    Toast.makeText(mActivity, "delete one item", Toast.LENGTH_LONG).show();
                    mList.remove(rxBusTestEvent.getRemoveIndex());
                    mAdapter.notifyDataSetChanged();
                });
    }

    private void initList() {
        if (mAdapter == null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(
                    new LinearDividerItemDecoration.Builder()
                            .setDividerColor(ContextCompat.getColor(mActivity, R.color.color_divider))
                            .setDividerHeight(1)
                            .isShowLastDivider(false)
                            .setOrientation(LinearLayoutManager.VERTICAL)
                            .setLeftMargin(Tools.dp2px(mActivity, 15))
                            .build()
            );

            mAdapter = new BaseBindingRecycleViewAdapter<>(mActivity,
                    R.layout.item_test, mList, HomeViewModel.this);

            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setDataSource(mList).notifyDataSetChanged();
        }
    }

    public int getReqIcon(String reqType) {
        return reqType.equals(TYPE_HOUSE_KEEPER_REQ_HELP)
                ? R.drawable.ic_house_keeper_help_circle
                : R.drawable.ic_house_keeper_repair_circle;
    }

    public void itemClicked(HomeTestBean bean) {
        Toast.makeText(mActivity, "you are here and can do anything. show item Id as sample: " + bean.getId(), Toast.LENGTH_LONG).show();
    }
}
