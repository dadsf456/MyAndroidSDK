package com.example.myandroidsdk.ui.activity;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.BeanList;
import com.example.myandroidsdk.ui.bean.Goods;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.example.myandroidsdk.ui.utils.DecimalUtil;
import com.example.myandroidsdk.ui.utils.GlideImageLoader;
import com.tool.cs.common.utils.DensityUtil;
import com.tool.cs.common.utils.GSONUtils;
import com.tool.cs.common.utils.LogUtils;
import com.tool.cs.common.utils.RxUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by fxb on 2020/7/3.
 */
public class SearchActivity extends BaseSecondActivity implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.et_search)
    AppCompatEditText et_search;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected String getBarTitleText() {
        return TextUtils.isEmpty(getIntent().getStringExtra("title")) ?
                "搜索" : getIntent().getStringExtra("title");
    }

    @Override
    protected int createContent() {
        return R.layout.activity_search;
    }

    @Override
    protected void initData() {
        cat_id = getIntent().getStringExtra("cat_id");
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        mRecyclerView.addItemDecoration(new ItemDecoration(DensityUtil.dp2px(this, 10)));
        et_search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();

                page = 1;
                request();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_search.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                keyword = et_search.getText().toString().trim();

                page = 1;
                request();
                return true;
            }
            return false;
        });

        mTabLayout.addTab(mTabLayout.newTab().setText("综合"));
        mTabLayout.addTab(mTabLayout.newTab().setText("最新"));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.tab_01));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(R.layout.tab_02));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                sort = tab.getPosition();

                if (sort == 0 || sort == 1) {
                    page = 1;
                    request();
                }

                if (sort != 2)
                    setDesc1(true);
                if (sort != 3)
                    setDesc2(true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        initTab3();
        initTab4();

        mAdapter.bindToRecyclerView(mRecyclerView);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {

//            Goods goods = mAdapter.getData().get(position);
//            Bundle bundle = new Bundle();
//            bundle.putString("goodsId", goods.id);
//            bundle.putInt("type", HomeData.TYPE_HOT);
//            startActivity(ProductActivity.class, bundle);
        });

        page = 1;
        request();
    }

    private ImageView iv1;
    private ImageView iv2;
    private Boolean isDesc1;//价格是否降序

    public void setDesc1(boolean clear) {
        if (clear) {
            isDesc1 = null;
            iv1.setImageResource(R.drawable.ic_aaa_);
            iv2.setImageResource(R.drawable.ic_bbb_);
            return;
        } else {
            if (isDesc1 == null)
                isDesc1 = true;
            else if (isDesc1)
                isDesc1 = false;
            else
                isDesc1 = null;
        }

        if (isDesc1 == null) {
            sort_type = null;
            iv1.setImageResource(R.drawable.ic_aaa_);
            iv2.setImageResource(R.drawable.ic_bbb_);
        } else if (isDesc1) {
            sort_type = 1;
            iv1.setImageResource(R.drawable.ic_aaa);
            iv2.setImageResource(R.drawable.ic_bbb_);
        } else {
            sort_type = 0;
            iv1.setImageResource(R.drawable.ic_aaa_);
            iv2.setImageResource(R.drawable.ic_bbb);
        }
        page = 1;
        request();
    }

    private void initTab3() {
        View view = mTabLayout.getTabAt(2).getCustomView();
        view.setOnClickListener(v -> {
            mTabLayout.getTabAt(2).select();

            setDesc1(false);
        });
        iv1 = view.findViewById(R.id.iv_1);
        iv2 = view.findViewById(R.id.iv_2);
    }

    private ImageView iv3;
    private ImageView iv4;
    private Boolean isDesc2;//销量是否降序

    public void setDesc2(boolean clear) {
        if (clear) {
            isDesc2 = null;
            iv3.setImageResource(R.drawable.ic_aaa_);
            iv4.setImageResource(R.drawable.ic_bbb_);
            return;
        } else {
            if (isDesc2 == null)
                isDesc2 = true;
            else if (isDesc2)
                isDesc2 = false;
            else
                isDesc2 = null;
        }
        if (isDesc2 == null) {
            sort_type = null;
            iv3.setImageResource(R.drawable.ic_aaa_);
            iv4.setImageResource(R.drawable.ic_bbb_);
        } else if (isDesc2) {
            sort_type = 1;
            iv3.setImageResource(R.drawable.ic_aaa);
            iv4.setImageResource(R.drawable.ic_bbb_);
        } else {
            sort_type = 0;
            iv3.setImageResource(R.drawable.ic_aaa_);
            iv4.setImageResource(R.drawable.ic_bbb);
        }
        page = 1;
        request();
    }

    private void initTab4() {
        View view = mTabLayout.getTabAt(3).getCustomView();
        view.setOnClickListener(v -> {
            mTabLayout.getTabAt(3).select();

            setDesc2(false);
        });
        iv3 = view.findViewById(R.id.iv_1);
        iv4 = view.findViewById(R.id.iv_2);
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        request();
    }

    private String cat_id;
    private String keyword;
    private int sort;
    private Integer sort_type;
    private int page;

    private void request() {
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(cat_id))
            params.put("cat_id", cat_id);
        if (!TextUtils.isEmpty(keyword))
            params.put("keyword", keyword);
        params.put("goods_type", 1);
        //params.put("course_id", 0);
        params.put("sort", sort);
        if (sort_type != null)
            params.put("sort_type", sort_type);
        params.put("page", page);

        LogUtils.e(TAG, GSONUtils.bean2Json(params));

        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getSearch(params)
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::getData)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<BeanList<Goods>>() {
                    @Override
                    public void onSuccess(BeanList<Goods> data) {
                        if (page == 1)
                            mAdapter.setNewData(null);

                        mAdapter.addData(data.getList());
                        mAdapter.notifyDataSetChanged();

                        if (data.noMoreData())
                            mAdapter.loadMoreEnd(false);
                        else
                            mAdapter.loadMoreComplete();

                        if (mAdapter.getData().isEmpty())
                            mAdapter.setEmptyView(R.layout.layout_empty_recycler, mRecyclerView);
                    }

                    @Override
                    public void onFailed(String message) {
                        showToast(message);
                    }
                });
    }

    private BaseQuickAdapter<Goods, BaseViewHolder> mAdapter =
            new BaseQuickAdapter<Goods, BaseViewHolder>(R.layout.item_home_hot_recycler) {
                @Override
                protected void convert(BaseViewHolder helper, Goods item) {
                    ImageView iv_product = helper.getView(R.id.iv_product);
                    GlideImageLoader.load(mContext, iv_product, item.pic_url, 0, R.drawable.ic_pictures_error, R.drawable.ic_pictures_error);
                    helper.setText(R.id.tv_product_name, item.name);
                    helper.setText(R.id.tv_product_price, DecimalUtil.format_append(item.price));
                    helper.setText(R.id.tv_product_amount, item.virtual_sales + "人已付款");
                }
            };

    private class ItemDecoration extends RecyclerView.ItemDecoration {
        private int spacing;

        public ItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view) - mAdapter.getHeaderLayoutCount();
            if (position < 0)
                return;

            int spanCount = 2;
            int column = position % spanCount;
            if (position < spanCount)
                outRect.top = spacing;
            outRect.left = spacing - column * spacing / spanCount;
            outRect.right = (column + 1) * spacing / spanCount;
            outRect.bottom = spacing;
        }
    }
}
