package com.example.myandroidsdk.ui.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myandroidsdk.R;
import com.example.myandroidsdk.ui.bean.BeanList;
import com.example.myandroidsdk.ui.bean.GoodsCategory;
import com.example.myandroidsdk.ui.global.Constant;
import com.example.myandroidsdk.ui.net.ApiService;
import com.example.myandroidsdk.ui.net.BaseObserver;
import com.example.myandroidsdk.ui.net.Response;
import com.example.myandroidsdk.ui.net.RetrofitUtils;
import com.example.myandroidsdk.ui.utils.GlideImageLoader;
import com.gyf.immersionbar.ImmersionBar;
import com.tool.cs.common.base.BaseActivity;
import com.tool.cs.common.utils.RxUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

public class DogTypeActivity extends BaseActivity {
    @BindView(R.id.mStatusBar)
    View mStatusBar;
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    Unbinder unbinder;
    @BindView(R.id.layout_error)
    LinearLayout layoutError;

    private String selectId = "";
    private String selectName = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dog_type;
    }

    @Override
    protected void initView() {
        super.initView();
        rvContent.setLayoutManager(new GridLayoutManager(this, 3));
        mAdapterTitle.bindToRecyclerView(rvCategory);
        mAdapterContent.bindToRecyclerView(rvContent);
        mAdapterContent.setHeaderView(getHeader());
        mAdapterTitle.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectItem(position);
            }
        });
        mAdapterContent.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Bundle bundle = new Bundle();
//                bundle.putString("title", selectName);
//                bundle.putString("cat_id", selectId);
//                startActivity(SearchActivity.class, bundle);
            }
        });

        initData1();
    }


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

    /**
     * 选中的第一级分类的索引
     */
    private int selectPos;

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

    /**
     * 右边内容
     */
    private BaseQuickAdapter<GoodsCategory, BaseViewHolder> mAdapterContent =
            new BaseQuickAdapter<GoodsCategory, BaseViewHolder>(R.layout.item_category_content_recycler) {
                @Override
                protected void convert(BaseViewHolder helper, GoodsCategory item) {
                    helper.setText(R.id.tv_product_name, item.name);

                    ImageView iv_product = helper.getView(R.id.iv_product);
                    GlideImageLoader.loadCategory(mContext, iv_product, item.pic_url);
                }
            };

    @OnClick(R.id.layout_error)
    public void onClickLayoutError() {
        layoutError.setVisibility(View.GONE);
        initData1();
    }


    private View header;
    private View iv_image;
    private TextView tv_title;

    private View getHeader() {
        if (header == null) {
            header = View.inflate(this, R.layout.header_categorys, null);
            iv_image = header.findViewById(R.id.iv_image);
            tv_title = header.findViewById(R.id.tv_title);
        }
        return header;
    }

    /**
     * 点击刷新数据
     *
     * @param index
     */
    private void selectItem(int index) {
        this.selectPos = index;


        GoodsCategory goodsCategory = mAdapterTitle.getData().get(index);

        selectId = goodsCategory.id;
        selectName = goodsCategory.name;

        mAdapterTitle.setNewData(mAdapterTitle.getData());
        if (!goodsCategory.list.isEmpty()) {
            mAdapterContent.setNewData(goodsCategory.list);
            tv_title.setText(goodsCategory.name);
        }
    }

    private void initData1() {


        RetrofitUtils.createService(ApiService.class, Constant.BASE_URL)
                .getShopCategories()
                .compose(RxUtils.switchObservableSchedulers())
                .map(Response::getData)
                .compose(RxUtils.bindEvent(this))
                .subscribe(new BaseObserver<BeanList<GoodsCategory>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        super.onSubscribe(d);
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                    }

                    @Override
                    public void onSuccess(BeanList<GoodsCategory> data) {
                        super.onSuccess(data);
                        mAdapterTitle.setNewData(data.getList());
                        if (mAdapterTitle.getData() != null) {
                            selectItem(0);
                        }
                    }

                    @Override
                    public void onFailed(String message) {
                        super.onFailed(message);
                        layoutError.setVisibility(View.VISIBLE);
                    }
                });
    }

}
