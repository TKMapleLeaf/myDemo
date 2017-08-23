package com.example.topnewrecyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by tangyangkai on 16/5/20.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;

    /**
     * @param space 传入的值，其单位视为dp
     */
    public SpaceItemDecoration(int space) {
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);

        outRect.left = mSpace;
        outRect.top = 0;
        outRect.bottom = mSpace;
        outRect.right = mSpace;
    }
}