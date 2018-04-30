package com.example.joy.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by joy on 2018/4/27.
 */

public class HorizontalView extends LinearLayout {
    private int mLastMoveX;
    private int mLastMoveY;
    private int leftBorder;
    private int rightBorder;
    private int mInterceptLastX;
    private int mInterceptLastY;
    private int mSlop;
    private Scroller mScroller;
    private int mChildCount;
    private int mChildIndex;
    private VelocityTracker mVelocityTracker;

    public HorizontalView(Context context) {
        super(context);
        init(context);
    }


    public HorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        mSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int childCount = getChildCount();
//        for(int i=0;i<childCount;i++) {
//            View child = getChildAt(i);
//            measureChild(child,widthMeasureSpec,heightMeasureSpec);
//        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        if (change) {
            mChildCount = getChildCount();
            for (int i = 0; i < mChildCount; i++) {
                View view = getChildAt(i);
                view.layout(i * view.getMeasuredWidth(), 0, (i + 1) * view.getMeasuredWidth(), view.getMeasuredHeight());
            }
            leftBorder = getLeft();
            rightBorder = mChildCount * getWidth();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int rawX = (int) ev.getRawX();
        int rawY = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int delatX = rawX - mLastMoveX;
                int diff = Math.abs(delatX) - mSlop;
                if (diff > 0) {
                    return true;
                }
                break;
        }

        mLastMoveX = rawX;
        mLastMoveY = rawY;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mVelocityTracker.addMovement(ev);
        int rawX = (int) ev.getRawX();
        int rawY = (int) ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                int detlaX = rawX - mLastMoveX;
                int detlaY = rawY - mLastMoveY;

//                if (getScrollX()+detlaX < leftBorder) {
//                    scrollTo(leftBorder, 0);
//                } else if (getScrollX() + getWidth() - detlaX > rightBorder) {
//                    scrollTo(rightBorder-getWidth(), 0);
//                }

                scrollBy(-detlaX, 0);

                mLastMoveX = rawX;
                mLastMoveY = rawY;
                break;

            case MotionEvent.ACTION_UP:

                mVelocityTracker.computeCurrentVelocity(2000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50) {
                    mChildIndex=xVelocity>0?mChildIndex-1:mChildIndex+1;
                }else{
                    mChildIndex=(getScrollX() + getWidth() / 2) / getWidth();
                }

                mChildIndex=Math.max(0,Math.min(mChildIndex,mChildCount-1));
                mScroller.startScroll(getScrollX(),0,mChildIndex*getWidth()-getScrollX(),0);
                invalidate();
                mVelocityTracker.clear();
//                if (getScrollX() < leftBorder) {
//                    mScroller.startScroll(getScrollX(),0,leftBorder-getScrollX(),0);
//                    invalidate();
//                    return true;
//                } else if (getScrollX() + getWidth() > rightBorder) {
//                    mScroller.startScroll(getScrollX(),0,rightBorder-getWidth()-getScrollX(),0);
//                    invalidate();
//                    return true;
//                }

//                int index = (getScrollX() + getWidth() / 2) / getWidth();
//                mScroller.startScroll(getScrollX(),0,index*getWidth()-getScrollX(),0);
//                invalidate();
                break;
        }


        return true;
    }
}
