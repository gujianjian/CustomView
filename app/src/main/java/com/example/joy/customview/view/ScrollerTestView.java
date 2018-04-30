package com.example.joy.customview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by joy on 2018/4/27.
 */

public class ScrollerTestView extends View {
    private Scroller mScroller;
    private float mLast;
    private float mLastX;
    private float mLastY;

    public ScrollerTestView(Context context) {
        super(context);
        init(context);
    }


    public ScrollerTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ScrollerTestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (MeasureSpec.AT_MOST == widthMode && MeasureSpec.AT_MOST == heightMode) {
            setMeasuredDimension(100, 100);
        } else if (MeasureSpec.AT_MOST == widthMode) {
            setMeasuredDimension(100, heightSize);
        } else if (MeasureSpec.AT_MOST == heightMode) {
            setMeasuredDimension(widthSize, 100);
        } else {
            setMeasuredDimension(widthSize, heightSize);
        }

    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            ((View)getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float rawX = event.getRawX();
        float rawY = event.getRawY();
        View parent = (View) getParent();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                int detlaX= (int) (rawX-mLastX);
                int detlaY= (int) (rawY-mLastY);

                parent.scrollBy(-detlaX,-detlaY);


                break;

            case MotionEvent.ACTION_UP:
                int index=(parent.getScrollX())/(parent.getWidth()/2);
                mScroller.startScroll(parent.getScrollX(),0,index*(parent.getWidth()/2)-parent.getScrollX(),0);
                invalidate();
                break;
        }
        mLastX =rawX;
        mLastY =rawY;

        return true;
    }
}
