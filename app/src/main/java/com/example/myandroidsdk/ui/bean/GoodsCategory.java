package com.example.myandroidsdk.ui.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxb on 2020/7/15.
 * 商品分类
 */
public class GoodsCategory implements MultiItemEntity {
    public String id;
    public String name;
    public String store_id;
    public String parent_id;
    public String pic_url;
    public String big_pic_url;
    public String advert_pic;
    public String advert_url;
    public List<GoodsCategory> list;

    public List<GoodsCategory> getList() {
        if (list == null)
            list = new ArrayList<>();
        return list;
    }

    public boolean isHeader = false;

    @Override
    public int getItemType() {
        return isHeader ? 1 : 0;
    }
}
