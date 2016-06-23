package com.wuxianedu.jminews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wuxianedu.jminews.fragment.BaseFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Hu131 on 2016/6/23.
 */
public class Main2Activity extends FragmentActivity{

    private ViewPager pager;
    private LinearLayout linearAdd;
    private ImageView imageMove;
    private HorizontalScrollView hsvMain;
    String[] strings = {"头条","社会","国内","国际","娱乐", "体育","军事","科技","财经","时尚"};
    private int tab_width; //每次滑动的屏幕宽度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        pager = (ViewPager) findViewById(R.id.view_pager_main);
        linearAdd = (LinearLayout) findViewById(R.id.linear_layout_add);
        imageMove = (ImageView) findViewById(R.id.image_view_move);
        hsvMain = (HorizontalScrollView) findViewById(R.id.hsv_view_main);

        initPager();
        initTab();
    }

    /**
     * 初始化导航栏
     */
    private void initTab() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 把当前屏幕的宽高像素写入dm对象中
        tab_width = (int)(dm.widthPixels / 4 + 0.5f);
        for (int i = 0; i<10; i++) {
            final TextView child = new TextView(this);
            child.setText(strings[i]);
            child.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (tab_width, LinearLayout.LayoutParams.MATCH_PARENT);
            child.setLayoutParams(params);
            linearAdd.addView(child);
            child.setTag(i);
            //点击导航栏，滑动viewpager
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pager.setCurrentItem((Integer)child.getTag());
                }
            });
        }
    }

    /**
     * 初始化viewpager+fragment
     */
    private void initPager() {

        //top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i<10; i++){
            Fragment fragment = new BaseFragment();
            Bundle bundle = new Bundle();
            bundle.putString("key",strings[i]);
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }
        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),fragmentList));
        pager.addOnPageChangeListener(new MyPagerChangedListen());

    }


    class MyFragmentPagerAdapter extends FragmentPagerAdapter{
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

    class MyPagerChangedListen implements ViewPager.OnPageChangeListener{

        /**
         * viewpager在滑动时调用
         * @param position
         * @param positionOffset
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * viewpager被选择后调用
         * @param position
         */
        @Override
        public void onPageSelected(int position) {

        }

        /**
         * viewpager状态改变时被调用
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
