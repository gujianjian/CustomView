package com.example.joy.customview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * Created by joy on 2018/4/12.
 */

public class CustomView extends View {
    private Scroller mScroller;

    private float mLastX;
    private float mLastY;

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void computeScroll() {

        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();

        }
    }

//    public void smoothScrollTo(int destX, int destY) {
//        int scrollX = getScrollX();
//        int delta = destX - scrollX;
//        mScroller.startScroll(scrollX, 0, delta, 0, 2000);
//        postInvalidate();
//    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float mViewX = event.getRawX();
        float mViewY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float deltaX = mViewX - mLastX;

                float delatY = mViewY - mLastY;
                View view=((View)getParent());
                //对左边越界的处理
                if(view.getScrollX()>getLeft()){
//                    offsetLeftAndRight(0);
//                    offsetTopAndBottom((int) delatY);
                view.scrollTo(0, getTop()+view.getScrollY());

                }
//                else if (-getScrollY()+getHeight()+delatY>view.getBottom()) {
//                    //对下边越界的处理else if (getY()+Math.abs(delatY)+getHeight()>view.getBottom())
////                    offsetLeftAndRight((int) deltaX);
////
////                    offsetTopAndBottom(0);
//                    ((View)getParent()).scrollTo(-view.getBottom(),-getScrollX());
//                } else {
//                    offsetLeftAndRight((int) deltaX);
//                    offsetTopAndBottom((int) delatY);
//                }
//                Log.d("aa", "deltaX:" + deltaX);
//                Log.d("aa", "deltaY:" + delatY);
                view.scrollBy(-(int) deltaX,-(int) delatY);
                Log.d("aa", "getY():" + getY());
                break;
            case MotionEvent.ACTION_UP:
//                View view2 = ((View) getParent());
//                mScroller.startScroll(view2.getScrollX(),view2.getScrollY()
//                        , getLeft()-view2.getScrollX(), getTop()-view2.getScrollY());
//                postInvalidate();
                break;


        }
        mLastX = mViewX;
        mLastY = mViewY;
        return super.onTouchEvent(event);
    }
}
