package com.wuxianedu.jminews.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hu131 on 2016/6/27.
 */
public class ResultBean {
    private String stat;
    private ArrayList<DataBean> data;

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public ArrayList<DataBean> getData() {
        return data;
    }

    public void setData(ArrayList<DataBean> data) {
        this.data = data;
    }
}
