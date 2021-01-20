package com.example.myandroidsdk.ui.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myandroidsdk.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tool.cs.common.utils.DensityUtil;
import com.tool.cs.common.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class RefreshActivity extends BaseSecondActivity {


    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int spacing;
    private List list = new ArrayList();

    @Override
    protected String getBarTitleText() {
        return "RefreshDemo";
    }

    @Override
    protected int createContent() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void initView() {
        super.initView();
        spacing = DensityUtil.dp2px(getBaseContext(), 10);

        list.add("");
        list.add("");
        list.add("");
        list.add("");

        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        //recycler.addItemDecoration(new PtMsItemDecoration());
        recycler.addItemDecoration(new HotItemDecoration());
        baseQuickAdapter.bindToRecyclerView(recycler);
        mRefreshLayout.setPrimaryColorsId(R.color.activityBg, R.color.colorAccent);
        //这个刷新可以设置全局的  在application里设置
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        mRefreshLayout.setRefreshFooter(new ClassicsFooter(this));
        baseQuickAdapter.setNewData(list);

        mRefreshLayout.autoRefresh();
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                MyToast.showToast(getBaseContext(), "下拉刷新");
                refreshLayout.finishRefresh(2000);
                refreshLayout.finishRefresh();
                list.clear();
                list.add("");
                list.add("");
                list.add("");
                list.add("");
                baseQuickAdapter.setNewData(list);
            }
        });
        //上拉刷新
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                MyToast.showToast(getBaseContext(), "上拉加载");
                refreshLayout.finishLoadMore(2000);
                list.add("");
                list.add("");
                list.add("");
                list.add("");
                baseQuickAdapter.setNewData(list);
            }
        });
    }


    private BaseQuickAdapter baseQuickAdapter =
            new BaseQuickAdapter<Object, BaseViewHolder>(R.layout.activity_refresh_item) {
                @Override
                protected void convert(BaseViewHolder helper, Object item) {

                }
            };


    /**
     * 横向
     */
    public class PtMsItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position == 0) {
                outRect.left = spacing;
            }
            outRect.right = spacing;
            outRect.top = spacing;
        }
    }

    /**
     * gridView
     */
    public class HotItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view) - baseQuickAdapter.getHeaderLayoutCount();
            if (position < 0)
                return;
            int spanCount = 2;
            int column = position % spanCount;
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;
            if (position < spanCount) {
                outRect.top = spacing;
            }
            outRect.bottom = spacing;
        }
    }

}
