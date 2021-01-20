package com.example.myandroidsdk.ui.activity;

import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.utils.GlideImageLoader;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tool.cs.common.base.BaseActivity;
import com.tool.cs.common.utils.DensityUtil;
import com.tool.cs.common.utils.MyToast;
import com.tool.cs.common.utils.ScreenUtils;
import com.tool.cs.common.widget.ComplexView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.bgabanner.BGABanner;

public class MoreItemActivity extends BaseActivity {


    @BindView(R.id.mStatusBar)
    View mStatusBar;
    @BindView(R.id.cv_search)
    ComplexView cvSearch;
    @BindView(R.id.mRecyclerView)
    RecyclerView recyclerView03;
    @BindView(R.id.mRefreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private List list00 = new ArrayList();
    private List list01 = new ArrayList();
    private List list02 = new ArrayList();
    private List list03 = new ArrayList();

    @Override
    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarView(mStatusBar)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarDarkIcon(true, 0.2f)
                .autoStatusBarDarkModeEnable(true, 0.2f)
                .autoNavigationBarDarkModeEnable(true, 0.2f)
                .init();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_more_layout;
    }

    @Override
    protected void initData() {
        super.initData();
        spacing = DensityUtil.dp2px(this, 10);


        list00.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1757702845,51708014&fm=26&gp=0.jpg");
        list00.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3201394637,2455584014&fm=26&gp=0.jpg");
        list00.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic1.win4000" +
                ".com%2Fpic%2F1%2F30%2F7a09511517.jpg&refer=http%3A%2F%2Fpic1.win4000.com&app=2002&size=f9999," +
                "10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1612103719&t=b6d4d0741b4174b670d2159bf3481bbc");

        list01.add("");
        list01.add("");
        list01.add("");
        list01.add("");
        list01.add("");
        list01.add("");
        list01.add("");
        list01.add("");


        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");
        list02.add("");

        list03.add("");
        list03.add("");
        list03.add("");
        list03.add("");
        list03.add("");
        list03.add("");
        list03.add("");

    }

    @Override
    protected void initView() {
        super.initView();
        cvSearch.setOnClickListener(view -> MyToast.showToast(getBaseContext(), "搜索"));
        getHeader();
        adapter03.addHeaderView(view);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(this));
        //  mRefreshLayout.setOnRefreshListener(refreshLayout -> MyToast.showToast(getBaseContext(),"刷新"));
        adapter01.setNewData(list01);
        adapter02.setNewData(list02);
        adapter03.setNewData(list03);

    }

    RecyclerView recyclerView01;
    RecyclerView recyclerView02;
    BGABanner bgaBanner;
    View view;

    private void getHeader() {
        view = LayoutInflater.from(this).inflate(R.layout.activity_more_layout_heard, null);
        bgaBanner = view.findViewById(R.id.bga_banner);
        recyclerView01 = view.findViewById(R.id.recycler01);
        recyclerView02 = view.findViewById(R.id.recycler02);

        //设置内容
        bgaBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(BGABanner banner, View itemView, @Nullable Object model, int position) {
                GlideImageLoader.load(MoreItemActivity.this.getBaseContext(),
                        (ImageView) itemView, model.toString(), 20,
                        R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            }
        });

        //设置圆角,BGAbanner不设置这个 自动滑屏的时候是直角
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            bgaBanner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 20);
                }
            });
            bgaBanner.setClipToOutline(true);
        }

        //点击事件
        bgaBanner.setDelegate((banner, itemView, model, position) -> {
            MyToast.showToast(getBaseContext(),
                    "我被点击了" + position);
        });
        bgaBanner.setData(list00, null);

        recyclerView01.setLayoutManager(new GridLayoutManager(this, 4));
        //  recyclerView02.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView03.setLayoutManager(new GridLayoutManager(this, 2));

        recyclerView02.addItemDecoration(new PtMsItemDecoration());
        recyclerView03.addItemDecoration(new HotItemDecoration());

        adapter01.bindToRecyclerView(recyclerView01);
        adapter02.bindToRecyclerView(recyclerView02);
        adapter03.bindToRecyclerView(recyclerView03);

        // recyclerView01.setLayoutManager(new GridLayoutManager(this, 4));
        // recyclerView02.setLayoutManager(new GridLayoutManager(this, 4));


    }


    private BaseQuickAdapter adapter01 = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.activity_more_item_02) {


        @Override
        protected void convert(BaseViewHolder helper, String item) {

        }
    };

    private BaseQuickAdapter adapter02 = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.activity_more_item_03) {


        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ViewGroup.LayoutParams layoutParams = helper.itemView.getLayoutParams();
            // layoutParams.height = DensityUtil.px2dp(getBaseContext(), ScreenUtils.getScreenWidth(getBaseContext())
            // /3);
            //   layoutParams.width = DensityUtil.px2dp(getBaseContext(), ScreenUtils.getScreenWidth(getBaseContext()
            //   )/3);
            layoutParams.width = (ScreenUtils.getScreenWidth(getBaseContext()) - DensityUtil.dp2px(getBaseContext(),
                    40)) / 3;
            layoutParams.width = (ScreenUtils.getScreenWidth(getBaseContext()) - DensityUtil.dp2px(getBaseContext(),
                    40)) / 3;
            helper.itemView.setLayoutParams(layoutParams);

            ImageView iv_product = helper.getView(R.id.iv_cat);
            GlideImageLoader.load(mContext, iv_product, "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u" +
                            "=1757702845,51708014&fm=26&gp=0.jpg", 0,
                    R.drawable.ic_pictures_error, R.drawable.ic_pictures_error);

            // GlideImageLoader.loadGoodsPic(mContext, view, item.getCover_pic());

        }
    };


    private BaseQuickAdapter adapter03 = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.activity_more_item_01) {
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            ImageView iv_product = helper.getView(R.id.iv_product);
            GlideImageLoader.load(mContext, iv_product, "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u" +
                            "=1757702845,51708014&fm=26&gp=0.jpg", 0,
                    R.drawable.ic_pictures_error, R.drawable.ic_pictures_error);
        }
    };

    /**
     * 设置itme的间距
     */

    private int spacing;

    public class PtMsItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position == 0) {
                outRect.left = spacing;
            }
            outRect.right = spacing;
            outRect.top = spacing;
        }
    }

    public class HotItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            int position =
                    parent.getChildAdapterPosition(view) - adapter03.getHeaderLayoutCount();
            if (position < 0) return;
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
