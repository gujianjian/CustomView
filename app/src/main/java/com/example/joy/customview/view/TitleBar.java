package com.example.joy.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.joy.customview.R;

/**
 * Created by joy on 2018/4/20.
 */

public class TitleBar extends RelativeLayout {

    private RelativeLayout rl_titlebar;
    private ImageView iv_titlebar_left;
    private ImageView iv_titlebar_right;
    private TextView tv_titlebar_title;
    private String mTitleText;
    private int mColorText;
    private int mColorTitleBg;

    public TitleBar(Context context) {
        super(context);
        initView(context);
    }


    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        //设置title
        mTitleText=a.getString(R.styleable.TitleBar_title_text);


        mColorText = a.getColor(R.styleable.TitleBar_title_text_color, Color.GREEN);

        mColorTitleBg = a.getColor(R.styleable.TitleBar_titlebar_bg, Color.GRAY);

        a.recycle();
        initView(context);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_title, this, true);
        rl_titlebar = findViewById(R.id.rl_titlebar);
        iv_titlebar_left = findViewById(R.id.iv_titlebar_left);
        iv_titlebar_right = findViewById(R.id.iv_titlebar_right);
        tv_titlebar_title = findViewById(R.id.tv_titlebar_title);

        tv_titlebar_title.setTextColor(mColorText);
        rl_titlebar.setBackgroundColor(mColorTitleBg);
        setTitleBarTitle(mTitleText);
    }

    public void setTitleBarTitle(String text){
        tv_titlebar_title.setText(text);
    }

    public void setLeftListener(OnClickListener onClickListener) {
        iv_titlebar_left.setOnClickListener(onClickListener);
    }

    public void setRightListener(OnClickListener onClickListener) {
        iv_titlebar_right.setOnClickListener(onClickListener);
    }

}
