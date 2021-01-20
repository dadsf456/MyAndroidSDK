package com.example.myandroidsdk.ui.fragment;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.activity.SearchActivity;
import com.example.myandroidsdk.ui.bean.BeanList;
import com.example.myandroidsdk.ui.bean.GoodsCategory;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.example.myandroidsdk.ui.utils.GlideImageLoader;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.base.BaseFragment;
import com.tool.cs.common.utils.RxUtils;
import com.tool.cs.common.widget.ComplexView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 作者：Created by dadsf456 on 2020-11-25 18:23
 * 邮箱：
 * 描述：
 */
public class MainFragment2 extends BaseFragment {
    @BindView(R.id.mStatusBar)
    View mStatusBar;
    @BindView(R.id.layout_error)
    LinearLayout layout_error;//页面出错，点击可重新加载
    @BindView(R.id.cv_search)
    ComplexView cv_search;
    @BindView(R.id.rv_category)
    RecyclerView rv_category;
    @BindView(R.id.rv_content)
    RecyclerView mRecyclerView;
    private String selectId = "";
    private String selectName = "";

    @Override
    protected void onFragmentVisibleChange(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            ImmersionBar.with(this)
                    .statusBarView(mStatusBar)
                    .statusBarDarkFont(true, 0.2f)
                    .navigationBarDarkIcon(true, 0.2f)
                    .autoStatusBarDarkModeEnable(true, 0.2f)
                    .autoNavigationBarDarkModeEnable(true, 0.2f)
                    .init();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dog_type;
    }

    @Override
    protected void initialize() {
        cv_search.setHint(R.string.hint_search_product);

        //设置一级分类
        rv_category.setAdapter(mAdapterTitle);
        mAdapterTitle.setOnItemClickListener((adapter, view, position) -> setSelectPos(position));

        //设置二级分类
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mRecyclerView.setAdapter(mAdapterContent);

        mAdapterContent.setHeaderView(getHeader());
        mAdapterContent.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putString("title", selectName);
            bundle.putString("cat_id", selectId);
            startActivity(SearchActivity.class, bundle);
        });

        invokeRequest();
    }

    private void invokeRequest() {
        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getShopCategories()
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::getData)
                .subscribe(new BaseObserver<BeanList<GoodsCategory>>() {
                    @Override
                    public void onSuccess(BeanList<GoodsCategory> data) {
                        mAdapterTitle.setNewData(data.getList());

                        //有数据才选择
                        if (!mAdapterTitle.getData().isEmpty())
                            setSelectPos(0);
                    }

                    @Override
                    public void onFailed(String message) {
                        showToast(message);
                        layout_error.setVisibility(View.VISIBLE);
                    }
                });
    }

    @OnClick(R.id.fl_search)
    public void onClickSearch() {
        startActivity(SearchActivity.class);
    }

    @OnClick(R.id.layout_error)
    public void onClickLayoutError() {
        layout_error.setVisibility(View.GONE);

        invokeRequest();
    }

    private View header;
    private ImageView iv_image;//广告图
    private TextView tv_title;

    private View getHeader() {
        if (header == null) {
            header = View.inflate(getContext(), R.layout.header_categorys, null);
            iv_image = header.findViewById(R.id.iv_image);
            tv_title = header.findViewById(R.id.tv_title);
        }
        return header;
    }

    /**
     * 选中的第一级分类的索引
     */
    private int selectPos;

    private void setSelectPos(int position) {
        this.selectPos = position;
        mAdapterTitle.notifyDataSetChanged();

        GoodsCategory data = mAdapterTitle.getData().get(position);

        mAdapterContent.setNewData(data.list);
        selectId = data.id;
        selectName = data.name;
        tv_title.setText(data.name);
        GlideImageLoader.load(getContext(), iv_image, data.pic_url, 0, R.drawable.ic_pictures_error,
                R.drawable.ic_pictures_error);
    }

    private BaseQuickAdapter<GoodsCategory, BaseViewHolder> mAdapterTitle =
            new BaseQuickAdapter<GoodsCategory, BaseViewHolder>(R.layout.item_category_tab_recycler) {
                @Override
                protected void convert(BaseViewHolder helper, GoodsCategory item) {
                    helper.setText(R.id.tv_tab, item.name);
                    if (helper.getAdapterPosition() == selectPos) {
                        helper.getView(R.id.tv_tab).setSelected(true);
                    } else {
                        helper.getView(R.id.tv_tab).setSelected(false);
                    }
                }
            };

    private BaseQuickAdapter<GoodsCategory, BaseViewHolder> mAdapterContent =
            new BaseQuickAdapter<GoodsCategory, BaseViewHolder>(R.layout.item_category_content_recycler) {
                @Override
                protected void convert(BaseViewHolder helper, GoodsCategory item) {
                    helper.setText(R.id.tv_product_name, item.name);

                    ImageView iv_product = helper.getView(R.id.iv_product);
                    GlideImageLoader.loadCategory(mContext, iv_product, item.pic_url);
                }
            };
}
