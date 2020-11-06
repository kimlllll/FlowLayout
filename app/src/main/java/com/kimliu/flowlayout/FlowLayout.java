package com.kimliu.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 流式布局，一般情况下子View是一个一个布局的，那么布局完一行，如果宽度不够了就会转到下一行
 * @Author: KimLiu
 * @CreateDate: 2020-10-16
 */
public class FlowLayout extends ViewGroup {

    private int mHorizontalSpacing = 32;// 每个item的横向间距
    private int mVerticalSpacing = 32; //每个item纵向间距

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 用来保存 所有的行数
    private List<List<View>> allLines = new ArrayList<>();
    private List<Integer> lineHeights = new ArrayList<>();

    private void clearMeasureParams(){
        allLines.clear();
        lineHeights.clear();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        clearMeasureParams();

        // 获取父View的padding值
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();

        // 获取爷爷View给父View的宽高
        // 在测量时，爷爷View其实并不知道父View 有多大，
        // 但是 爷爷View会给父View 一个宽高的限制，
        // 这两个参数就是爷爷View给父View的宽高限制，并不是确切的值，
        // 只是在测量时作为一个参考 父View在计算自己的尺寸的时候，需要遵循这两个参数所包含的限制
        int selfWidth = MeasureSpec.getSize(widthMeasureSpec);
        int selfHeight = MeasureSpec.getSize(heightMeasureSpec);


        // 用一个集合保存每一行的View
        List<View> lineViews = new ArrayList<>();
        // 每一行已经使用的宽度
        int lineWidthUsed = 0;
        // 每一行的高度
        int lineHeight = 0;

        //measure过程中，子View要求的父ViewGroup的宽高 也就是最后setMeasuredDimension时，保存的自己的宽高
        int parentNeededHeight = 0;
        int parentNeedWidth = 0;


        // 先度量孩子，再度量自己

        // 先度量孩子
        int childCount = getChildCount();
        for (int i = 0; i < childCount;i++){
            View childView = getChildAt(i);
            LayoutParams childViewLayoutParams = childView.getLayoutParams();
            //根据父View的MeasureSpec 计算出子View 的MeasureSpec
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, paddingLeft + paddingRight, childViewLayoutParams.width);
            int ChildHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, paddingTop + paddingBottom, childViewLayoutParams.height);

            // 子View进行自我测量
            childView.measure(childWidthMeasureSpec,ChildHeightMeasureSpec);

            // 度量之后，就可以获取到子View的度量宽高
            int childViewMeasuredHeight = childView.getMeasuredHeight();
            int childViewMeasuredWidth = childView.getMeasuredWidth();

            // 需要换行 这一行的子view的宽度 + 已使用的宽度 + 横向的空格宽度 > 父View的宽度 则需要换行
            if(childViewMeasuredWidth + lineWidthUsed + mHorizontalSpacing > selfWidth){
                // 换行时，记录当前这一行，放到allLines中
                allLines.add(lineViews);
                // 把行高也记录下来
                lineHeights.add(lineHeight);

                // 获取子View希望的父View的宽高
                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeedWidth = Math.max(parentNeedWidth,lineWidthUsed + mHorizontalSpacing);

                // 清空上一行的数据
                lineViews = new ArrayList<>();
                lineWidthUsed = 0;
                lineHeight = 0;

            }
            //每一行的View都添加到lineView集合中
            lineViews.add(childView);
            //每一行已经使用的宽
            lineWidthUsed = lineWidthUsed + childViewMeasuredWidth + mHorizontalSpacing;
            lineHeight = Math.max(lineHeight,childViewMeasuredHeight);


            // 最后一行，不会换行，所以不会走换行的逻辑，要单独处理
            if(i == childCount-1){
                allLines.add(lineViews);
                lineHeights.add(lineHeight);
                parentNeededHeight = parentNeededHeight + lineHeight + mVerticalSpacing;
                parentNeedWidth = Math.max(parentNeedWidth, lineWidthUsed + mHorizontalSpacing);
            }
        }

        // 度量自己，把自己的宽高保存
        // 根据子View的度量结果，再来重新度量自己，但是在度量自己的过程中，也必须遵循爷爷View给自己的限制
        int selfWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int selfHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 爷爷View 给父View的限制中的模式，只有当模式为EXACLTY时，也就是父View的宽高是确定值得时候，使用
        // selfwidth 和 selfheight
        int realWidth = (selfWidthMode == MeasureSpec.EXACTLY) ? selfWidth : parentNeedWidth;
        int realHeight = (selfHeightMode == MeasureSpec.EXACTLY) ? selfHeight : parentNeededHeight;

        setMeasuredDimension(realWidth,realHeight);
    }

    // 布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int lineCount = allLines.size();

        // 获取左侧padding
        int curL = getPaddingLeft();
        // 获取顶部的padding
        int curT = getPaddingTop();

        //一行一行的布局
        for(int i = 0;i < lineCount;i++){
            // 获取行
            List<View> viewList = allLines.get(i);
            // 获取这一行的高度
            int lineHeight = lineHeights.get(i);
            for(int j = 0;j<viewList.size();j++){
                // 布局一行
                // 获取View
                View view = viewList.get(j);

                int left = curL;// 每一行的第一个的左侧坐标是 == paddingLeft
                int top = curT;
                // 获取右侧和下侧的值
                int right = left + view.getMeasuredWidth();
                int bottom = top + view.getMeasuredHeight();
                view.layout(left,top,right,bottom);
                curL = right + mHorizontalSpacing; // 下一个View的左侧坐标 = 上一个View的右侧坐标+横向间隔值
            }
            // 一行布局完成之后，再重新获取PaddingTop和PaddingLeft
            // 下一行paddingTop = 一开始的paddingTop+行高+纵向间距
            curT = curT + lineHeight + mVerticalSpacing;
            curL = getPaddingLeft();// 每一行的curL要重新清零
        }


    }
}
