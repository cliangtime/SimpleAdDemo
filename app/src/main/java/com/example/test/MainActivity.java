package com.example.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageBanner mImageBanner;
    private ImageBanner mImageBanner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageBanner = (ImageBanner) findViewById(R.id.photo);
        mImageBanner1 = (ImageBanner) findViewById(R.id.photo1);
        mImageBanner.startLooper();
        mImageBanner.addImage(getImageView(R.mipmap.smiley_0));
        mImageBanner.addImage(getImageView(R.mipmap.smiley_1));
        mImageBanner.addImage(getImageView(R.mipmap.smiley_2));
        mImageBanner.addImage(getImageView(R.mipmap.smiley_3));
        mImageBanner.addImage(getImageView(R.mipmap.smiley_4));

        mImageBanner1.startLooper();
        mImageBanner1.addImage(getImageView(R.mipmap.smiley_0));
        mImageBanner1.addImage(getImageView(R.mipmap.smiley_1));
        mImageBanner1.addImage(getImageView(R.mipmap.smiley_2));
        mImageBanner1.addImage(getImageView(R.mipmap.smiley_3));
        mImageBanner1.addImage(getImageView(R.mipmap.smiley_4));

        mImageBanner.setOnViewPagerClickListener(new ImageBanner.OnViewPagerClickListener() {
            @Override
            public void ClickListener(View v, int position) {
                Toast.makeText(MainActivity.this,position+"被点击了",Toast.LENGTH_SHORT).show();
            }
        });
        mImageBanner1.setOnViewPagerClickListener(new ImageBanner.OnViewPagerClickListener() {
            @Override
            public void ClickListener(View v, int position) {
                Toast.makeText(MainActivity.this,position+"被点击了",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ImageView getImageView(int resId) {
        //填充的布局
        ImageView image = new ImageView(this);
        image.setImageResource(resId);
        return image;
    }


    //必须要移除Handler的消息，不然容易造成内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mImageBanner.reMoveLooper();
    }
}
