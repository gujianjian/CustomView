package com.example.joy.customview;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.joy.customview.view.CustomView;
import com.example.joy.customview.view.TitleBar;

public class MainActivity extends AppCompatActivity {

//    private TitleBar mTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ActionBar supportActionBar = getSupportActionBar();
//        if(supportActionBar!=null)
//        {
//            supportActionBar.setElevation(0);
//        }
//
//        initView();
    }

//    private void initView() {
//        mTitleBar = findViewById(R.id.mTitleBar);
//
//        mTitleBar.setLeftListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//        mTitleBar.setRightListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(MainActivity.this,"我是右边的",Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}
