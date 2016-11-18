# SimpleAdDemo
一个简易的viewPager实现的自定义广告条

###使用方法<其实就是一个自定义控件>(还有很多未知的bug)
1. 复制 ImageBanner这个复制到你的工程中
//使用代码进行设置

           imageBanner = new ImageBanner(getActivity());

||接4

2. 把自定义View引入到布局文件中去，并定义一个id

	    <com.example.test.ImageBanner
	    android:id="@+id/photo"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"/>
3. 通过findViewById找到这个控件
	
		mImageBanner = (ImageBanner) findViewById(R.id.photo);

接1：

4. 设置是否展示自动滚动的轮播图
		
		mImageBanner.startLooper();
 
5. 动态添加图片，想添加几张就添加几张
		
		 mImageBanner.addImage(R.mipmap.smiley_0);
         mImageBanner.addImage(R.mipmap.smiley_1);
         mImageBanner.addImage(R.mipmap.smiley_2);
6. 设置图片的缩放类型


7. viewpager的点击事件

		 mImageBanner.setOnViewPagerClickListener(new ImageBanner.OnViewPagerClickListener() {
		            @Override
		            public void ClickListener(View v, int position) {
		                Toast.makeText(MainActivity.this,position+"被点击了",Toast.LENGTH_SHORT).show();
		            }
		        });
8. 最后一步

		//mImageBanner.reMoveLooper(); 停止轮播图的自动展示

		   //必须要移除Handler的消息，不然容易造成内存泄漏
		    @Override
		    protected void onDestroy() {
		        super.onDestroy();
		        mImageBanner.reMoveLooper();
		    }
