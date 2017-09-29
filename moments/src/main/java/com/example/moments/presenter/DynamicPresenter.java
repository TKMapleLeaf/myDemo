package com.example.moments.presenter;


import com.example.moments.basemvp.MvpBasePresenter;
import com.example.moments.basemvp.MvpCommonCallBack;
import com.example.moments.bean.DynamicBean;
import com.example.moments.model.DynamicModel;
import com.example.moments.view.DynamicView;

/**
 * Created by Administrator on 2017/9/14.
 */

public class DynamicPresenter extends MvpBasePresenter<DynamicView> {

    private DynamicModel mDynamicModel;

    public DynamicPresenter() {
        mDynamicModel = new DynamicModel();
    }

    public void dynamic(String page) {

        mDynamicModel.dynamic(page,new MvpCommonCallBack<DynamicBean>() {
            @Override
            public void onSuccess(DynamicBean dynamicBean) {
                if (getView() != null) {
                    getView().success(dynamicBean);
                }
            }

            @Override
            public void onError(String err) {
                if (getView() != null) {
                    getView().failure(err);
                }
            }
        });
    }

    @Override
    public void cancelRequests() {
        mDynamicModel.cancelRequests();
    }
}
