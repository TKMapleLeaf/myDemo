package com.example.moments;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.moments.bean.DynamicBean;
import com.example.moments.bean.PhotoInfo;
import com.example.moments.widget.GlideRoundTransform;
import com.example.moments.widget.MultiImageView;

import java.util.List;


/**
 * Created by Administrator on 2017/8/30.
 */

public class DynamicAdapter extends BaseQuickAdapter<DynamicBean.Dynamic, BaseViewHolder> {


    public DynamicAdapter(@LayoutRes int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, DynamicBean.Dynamic item) {

        helper.setText(R.id.item_attention_tv_name, item.getName());
        helper.setText(R.id.item_attention_tv_des, item.getDes());
        helper.setText(R.id.item_attention_tv_job, item.getJob());
        helper.setText(R.id.item_attention_tv_content, item.getContent());

        ImageView ivHead = helper.getView(R.id.item_attention_iv_head);
        Glide.with(MyApplication.applicationContext)
                .load(item.getPath())
                .transform(new GlideRoundTransform(MyApplication.applicationContext, 5))
                .into(ivHead);

        MultiImageView multiImageView = helper.getView(R.id.item_attention_iv_logo);
        List<PhotoInfo> photes = item.getPhotes();
        if (photes != null && photes.size() > 0) {
            multiImageView.setVisibility(View.VISIBLE);
            multiImageView.setList(photes);
            multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });
        } else {
            multiImageView.setVisibility(View.GONE);
        }

    }

}
