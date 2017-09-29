package com.example.moments;

import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.moments.bean.DynamicBean;


/**
 * Created by Administrator on 2017/8/30.
 */

public class DynamicAdapter1 extends BaseQuickAdapter<DynamicBean.Dynamic, BaseViewHolder> {


    public DynamicAdapter1(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicBean.Dynamic item) {

        helper.setText(R.id.item_attention_tv_name1, item.getName());

    }

}
