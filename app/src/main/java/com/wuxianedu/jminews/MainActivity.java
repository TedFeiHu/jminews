package com.wuxianedu.jminews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private HorizontalScrollView mHorizontalScrollView;
    private LinearLayout mLinearTab;
    private ViewPager mPager;
    private int tab_width;
    private ArrayList<Fragment> fragments;
    private ImageView imgTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.hsv_view);
        mLinearTab = (LinearLayout) findViewById(R.id.hsv_content);
        mPager = (ViewPager) findViewById(R.id.view_pager);

        imgTab = (ImageView) findViewById(R.id.img_tab);
        initTab();

        initPager();
    }

    /**
     * 初始化viewpager
     */
    private void initPager() {
        fragments = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Bundle bundle = new Bundle();
            bundle.putString("key", i + 1 + "");
            BlankFragment blankFragment = new BlankFragment();
            blankFragment.setArguments(bundle);
            fragments.add(blankFragment);
        }

        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        mPager.setCurrentItem(0);
        mPager.addOnPageChangeListener(new MyPagerChangedListener());
    }

    /**
     * 初始化导航栏
     */
    private void initTab() {
        //得到屏幕的像素宽高
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Log.i("---main--", dm.toString());
        tab_width = (int) (dm.widthPixels / 4 + 0.5f);

        for (int i = 0; i < 7; i++) {
            RelativeLayout rl = new RelativeLayout(this);
            TextView tabName = new TextView(this);
            tabName.setText("第" + i + "个");
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    (RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            rl.addView(tabName, params);
            mLinearTab.addView(rl, tab_width, 110);
            rl.setTag(i);
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPager.setCurrentItem((Integer) v.getTag());
                }
            });
        }
    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> list;

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            this.list = list;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    class MyPagerChangedListener implements ViewPager.OnPageChangeListener {

        private int endPosition;
        private int beginPosition;
        private int currentFragmentIndex;
        private boolean isEnd;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (!isEnd) {//是否正在滑动
                if (currentFragmentIndex == position) {
                    endPosition = tab_width * currentFragmentIndex +
                            (int) (tab_width * positionOffset);
                }
                if (currentFragmentIndex == position + 1) {
                    endPosition = tab_width * currentFragmentIndex -
                            (int) (tab_width * (1 - positionOffset));
                }

                Animation mAnimation = new TranslateAnimation(beginPosition, endPosition, 0, 0);
                mAnimation.setFillAfter(true);
                mAnimation.setDuration(0);
                imgTab.startAnimation(mAnimation);
                //         mHorizontalScrollView.invalidate();
                beginPosition = endPosition;

                Log.i("-----position-----", position + "");
                Log.i("-----currentFragment-", currentFragmentIndex + "");
            }
        }

        @Override
        public void onPageSelected(int position) {
            Animation animation = new TranslateAnimation(endPosition, position * tab_width, 0, 0);

            beginPosition = position * tab_width;

            currentFragmentIndex = position;

            animation.setFillAfter(true);
            animation.setDuration(0);
            imgTab.startAnimation(animation);
            mHorizontalScrollView.smoothScrollTo((currentFragmentIndex - 1) * tab_width, 0);


            Log.i("----------", "position:" + position + "beginPosition:" + beginPosition
                    + "currentFragmentIndex:" + currentFragmentIndex + "endPosition:" + endPosition);

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                isEnd = false;
            } else {
                if (state == ViewPager.SCROLL_STATE_SETTLING) {
                    isEnd = true;
                    beginPosition = currentFragmentIndex * tab_width;
                    if (mPager.getCurrentItem() == currentFragmentIndex) {
                        // 未跳入下一个页面
                        imgTab.clearAnimation();
                        Animation animation = null;
                        // 恢复位置
                        animation = new TranslateAnimation(endPosition, currentFragmentIndex * tab_width, 0, 0);
                        animation.setFillAfter(true);
                        animation.setDuration(1);
                        imgTab.startAnimation(animation);
                        //   mHorizontalScrollView.invalidate();
                        endPosition = currentFragmentIndex * tab_width;
                    }
                }
            }
        }
    }
}
