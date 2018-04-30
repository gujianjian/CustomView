package com.example.joy.customview.view.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by joy on 2018/4/30.
 */

public class HuaDongView extends ViewGroup {

    private int lastMaxChildHeight;
    private int totalChildWidth;
    private int mSlop;
    private int lastY;
    private int lastX;
    private int leftBorder;
    private int rightBorder;
    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int childCount;
    private int childIndex;

    public HuaDongView(Context context) {
        super(context);
        init(context);
    }

    public HuaDongView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HuaDongView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScroller = new Scroller(context);
        mVelocityTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //对所有子view进行测量，这会触发每个measureChild
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        totalChildWidth = 0;
        lastMaxChildHeight = 0;

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            getMaxHeightAndTotalWidth(childCount);
            //宽按子view累加，宽按最大来测量父view
            setMeasuredDimension(totalChildWidth, lastMaxChildHeight);

        } else if (widthMode == MeasureSpec.AT_MOST) {
            getMaxHeightAndTotalWidth(childCount);
            setMeasuredDimension(totalChildWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            getMaxHeightAndTotalWidth(childCount);
            setMeasuredDimension(widthSize, lastMaxChildHeight);
        }

        leftBorder=getLeft();
        rightBorder=totalChildWidth;
    }


    @Override
    protected void onLayout(boolean change, int l, int t, int r, int b) {
        childCount = getChildCount();
        int currentChildWidth = 0;
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            currentChildWidth += child.getMeasuredWidth();
            child.layout(currentChildWidth - child.getMeasuredWidth(), 0, currentChildWidth, child.getMeasuredHeight());
        }
    }

    /**
     * 计算总的子view宽度和子view最高高度
     *
     * @param childCount
     */
    private void getMaxHeightAndTotalWidth(int childCount) {
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int measuredWidth = child.getMeasuredWidth();
            totalChildWidth += measuredWidth;
            if (child.getMeasuredHeight() > lastMaxChildHeight) {
                lastMaxChildHeight = child.getMeasuredHeight();
            }

        }
    }

    /**
     * 拦截子view的事件，如果滑动大于mSlop(常量8)
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int rawX = (int) ev.getRawX();
                int rawY = (int) ev.getRawY();
                lastX = rawX;
                lastY = rawY;
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                int moveY = (int) ev.getRawY();

                int detlaX=moveX-lastX;
                int diff=Math.abs(detlaX)-mSlop;
                if(diff>0){
                    return true;
                }

                lastX = moveX;
                lastY = moveY;
                break;
            default:
                break;
        }


        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //不要忘记添加
        mVelocityTracker.addMovement(ev);
        switch (ev.getAction()) {

            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getRawX();
                int moveY = (int) ev.getRawY();

                int detlaX=moveX-lastX;

                //保护左边界
//                if(getScrollX()<leftBorder){
//                    scrollTo(leftBorder,0);
//                    return true;
//                }
//
//                //保护右边界
//                if (getScrollX() + getWidth()> rightBorder) {
//                    scrollTo(rightBorder-getWidth(),0);
//                    return true;
//                }
                scrollBy(-detlaX,0);

                lastX = moveX;
                lastY = moveY;
                break;
            case MotionEvent.ACTION_UP:
                int viewWidth=getWidth();
                int mScrollX=getScrollX();

                // mVelocityTracker.addMovement(ev);不要忘记添加
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();//获得水平速度，获得之前需要调用上面那个方法
                if (Math.abs(xVelocity) > 50) {
                    //如果速度为正，则是往右滑则-1，反之加1
                    childIndex=xVelocity>0?childIndex-1:childIndex+1;
                }
                else{
                    childIndex = (mScrollX+viewWidth/2)/viewWidth;
//                    mScroller.startScroll(getScrollX(),0,index*viewWidth-getScrollX(),0);
                }
                //对边界大小就行保护，在0到childcount之间
                childIndex=Math.max(0,Math.min(childIndex, childCount-1));
                mScroller.startScroll(getScrollX(),0,childIndex*viewWidth-getScrollX(),0);
                invalidate();
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
