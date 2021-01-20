package com.example.myandroidsdk.ui.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fxb on 2020/7/8.
 */
public class BeanList<T> {
    @SerializedName(value = "list", alternate = {"study_list", "comment", "goods_list", "mch_list"})
    private List<T> list;
    @SerializedName("page_count")
    private int pages;//总页数
    @SerializedName("row_count")
    private int amount;//当前页数据个数
    @SerializedName("status")
    private int status;//当前状态
    @SerializedName("is_search")
    private int is_search;//当前状态
    public List<T> getList() {
        return list == null ? new ArrayList<>() : list;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIs_search() {
        return is_search;
    }

    public void setIs_search(int is_search) {
        this.is_search = is_search;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean noMoreData() {
        return pages <= 1 || getList().isEmpty();
    }
}
