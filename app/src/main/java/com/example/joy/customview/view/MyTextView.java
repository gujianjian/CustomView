package com.example.joy.customview.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by joy on 2018/4/20.
 */

@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public MyTextView(Context context) {
        super(context);
        initDraw();
    }


    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initDraw();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDraw();
    }

    private void initDraw() {
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth((float) 1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x=getWidth();
        int y=getHeight();
        canvas.drawLine(0,y/2,x,y/2,mPaint);
    }
}
