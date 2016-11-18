package com.example.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageBanner mImageBanner;
    private ImageBanner mImageBanner_Code;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout) findViewById(R.id.activity_main);
        mImageBanner = (ImageBanner) findViewById(R.id.photo);

        mImageBanner_Code = new ImageBanner(App.sContext);
        mLinearLayout.addView(mImageBanner_Code);
        //布局
        mImageBanner.startLooper();
        mImageBanner.setImagesScaleType(ImageView.ScaleType.CENTER);
        mImageBanner.addImage(R.mipmap.smiley_0);
        mImageBanner.addImage(R.mipmap.smiley_1);
        mImageBanner.addImage(R.mipmap.smiley_2);
        mImageBanner.addImage(R.mipmap.smiley_3);
        mImageBanner.addImage(R.mipmap.smiley_4);
        mImageBanner.addImage(R.mipmap.bg);
        //代码
        mImageBanner_Code.startLooper();
        mImageBanner_Code.setImagesScaleType(ImageView.ScaleType.CENTER_INSIDE);
        mImageBanner_Code.addImage(R.mipmap.bg);
        mImageBanner_Code.addImage(R.mipmap.smiley_0);
        mImageBanner_Code.addImage(R.mipmap.smiley_1);
        mImageBanner_Code.addImage(R.mipmap.smiley_2);
        mImageBanner_Code.addImage(R.mipmap.smiley_3);
        mImageBanner_Code.addImage(R.mipmap.smiley_4);


        mImageBanner.setOnViewPagerClickListener(new ImageBanner.OnViewPagerClickListener() {
            @Override
            public void ClickListener(View v, int position) {
                Toast.makeText(App.sContext,position+"被点击了",Toast.LENGTH_SHORT).show();
            }
        });
        mImageBanner_Code.setOnViewPagerClickListener(new ImageBanner.OnViewPagerClickListener() {
            @Override
            public void ClickListener(View v, int position) {
                Toast.makeText(App.sContext,position+"被点击了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //必须要移除Handler的消息，不然容易造成内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageBanner.reMoveLooper();
        mImageBanner_Code.reMoveLooper();
    }
}
