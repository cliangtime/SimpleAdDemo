package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by yo
 *
 * @创建时间 2016/11/12 18:41
 * @描述 viewpager广告轮播图
 */

public class ImageBanner extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnTouchListener {

    private static final int UNSELECTED_DOT_COLOR = Color.LTGRAY;//小圆点未选中的颜色
    private static final int SELECTED_DOT_COLOR = Color.DKGRAY;  //小圆点选中的颜色
    private static final int DEFAULT_DOT_SIZE = 10;              //底部小圆点的大小
    private static final long times = 2000;                     //每张图片的轮播事件

    private int x1;
    private ViewPager mViewPager;
    private LinearLayout ll_dots;

    private ArrayList<View> images = new ArrayList<>();
    private ArrayList<Dot> dots = new ArrayList<>();

    private PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // container.removeView((ImageView) object);  为了实现无限轮播
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            final int position_in_data = position % images.size();
            View image = images.get(position_in_data);
            //图片的点击事件
            image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnViewPagerClickListener != null) {
                        mOnViewPagerClickListener.ClickListener(v, position_in_data);
                    }
                }
            });

            //view的长按点击事件
            image.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    reMoveLooper();
                    return false;
                }
            });
            if (image.getParent() != null) {
                ViewGroup viewGroup = (ViewGroup) image.getParent();
                viewGroup.removeView(image);
            }
            container.addView(image);
            return image;
        }
    };

    //定义一个viewpager的点击事件的接口回调
    public interface OnViewPagerClickListener {
        void ClickListener(View v, int position_in_data);
    }

    private OnViewPagerClickListener mOnViewPagerClickListener;

    public void setOnViewPagerClickListener(OnViewPagerClickListener mOnViewPagerClickListener) {
        this.mOnViewPagerClickListener = mOnViewPagerClickListener;
    }


    public ImageBanner(Context context) {
        this(context, null);
    }

    public ImageBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        //动态添加ViewPager实例
        mViewPager = new ViewPager(context);
        mViewPager.setAdapter(adapter);
        //由于ViewPager 没有点击事件，可以通过对VIewPager的setOnTouchListener进行监听已达到实现点击事件的效果
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);
        addView(mViewPager);

        //动态添加一个LinearLayout
        ll_dots = new LinearLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //TODO:小圆点的位置
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(CENTER_HORIZONTAL);
        params.setMargins(10, 10, 10, 10);
        addView(ll_dots, params);
    }

    private LinearLayout.LayoutParams params;

    //初始化数据
    public void addImage(View view) {
        if (params == null) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
        }
        Dot dot = new Dot(getContext());
        dots.add(dot);
        ll_dots.addView(dot, params);
        images.add(view);
        adapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(images.size() * 1024);
        refreshDots(0);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //图片切换监听
                    int currentItem = mViewPager.getCurrentItem();
                    mViewPager.setCurrentItem(currentItem + 1);
                    onPageSelected(currentItem + 1);
                    mHandler.sendEmptyMessageDelayed(0, times);
                    break;
            }
        }
    };

    /***
     * 自动轮播功能
     */
    public void startLooper() {
        mHandler.sendEmptyMessageDelayed(0, times);
    }

    //不使用无限轮播
    public void reMoveLooper() {
        mHandler.removeCallbacksAndMessages(null);
    }


    private void refreshDots(int position_in_data) {
        for (Dot dot : dots) {
            dot.setDotColor(UNSELECTED_DOT_COLOR);
        }
        dots.get(position_in_data).setDotColor(SELECTED_DOT_COLOR);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int position_in_data = position % images.size();
        refreshDots(position_in_data);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = (int) event.getX();
                reMoveLooper();
                break;
        }
        return super.onTouchEvent(event);
    }

    //由于ViewPager 没有点击事件，可以通过对VIewPager的setOnTouchListener进行监听已达到实现点击事件的效果
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        reMoveLooper();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                int x2 = (int) event.getX();
                if (Math.abs(x1 - x2) < 6) {
                    mHandler.sendEmptyMessageDelayed(0, times);
                    return false;// 距离较小，当作click事件来处理
                }
                if (Math.abs(x1 - x2) > 6) {  // 真正的onTouch事件
                    mHandler.sendEmptyMessageDelayed(0, times);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return false;
    }

    //定义Dot类，可以表示当前viewpager处于第几个页面
    private class Dot extends View {

        private Paint dotPaint;
        private int dotColor = UNSELECTED_DOT_COLOR;

        public Dot(Context context) {
            super(context);

            dotPaint = new Paint();
            dotPaint.setAntiAlias(true);
            dotPaint.setDither(true);
            dotPaint.setStyle(Paint.Style.FILL);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            dotPaint.setColor(dotColor);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, dotPaint);
        }

        //这一步的作用是把dot的宽度和高度定死
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_DOT_SIZE, MeasureSpec.EXACTLY);
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(DEFAULT_DOT_SIZE, MeasureSpec.EXACTLY);
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }

        //修改dot的颜色，并且让整个dot重绘
        public void setDotColor(int color) {
            dotColor = color;
            invalidate();
        }
    }
}
