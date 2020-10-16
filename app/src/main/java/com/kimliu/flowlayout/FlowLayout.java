package com.kimliu.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * @Description:
 * @Author: KimLiu
 * @CreateDate: 2020-10-16
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
