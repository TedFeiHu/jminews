package com.wuxianedu.jminews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuxianedu.jminews.fragment.CaiJingFragment;
import com.wuxianedu.jminews.fragment.GuoJiFragment;
import com.wuxianedu.jminews.fragment.GuoNeiFragment;
import com.wuxianedu.jminews.fragment.KeJiFragment;
import com.wuxianedu.jminews.fragment.SheHuiFragment;
import com.wuxianedu.jminews.fragment.ShiShangFragment;
import com.wuxianedu.jminews.fragment.TiYuFragment;
import com.wuxianedu.jminews.fragment.TopFragment;
import com.wuxianedu.jminews.fragment.YuLeFragment;
import com.wuxianedu.jminews.fragment.junShiFragment;

import java.util.ArrayList;

/**
 * Created by Hu131 on 2016/6/23.
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager pager;
    private LinearLayout linearAdd;
    private ImageView imageMove;
    private HorizontalScrollView hsvMain;
    String[] strings = {"头条", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private int tab_width; //每次滑动的屏幕宽度


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        pager = (ViewPager) findViewById(R.id.view_pager_main);
        linearAdd = (LinearLayout) findViewById(R.id.linear_layout_add);
        imageMove = (ImageView) findViewById(R.id.image_view_move);
        hsvMain = (HorizontalScrollView) findViewById(R.id.hsv_view_main);
        //标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("海事新闻");

        // 初始化，带有侧滑的布局
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //侧滑布局
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initPager();
        initTab();
    }

    /**
     * 初始化导航栏
     */
    private void initTab() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm); // 把当前屏幕的宽高像素写入dm对象中
        tab_width = (int) (dm.widthPixels / 4 + 0.5f);
        for (int i = 0; i < strings.length; i++) {
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
                    pager.setCurrentItem((Integer) child.getTag());
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
        fragmentList.add(new TopFragment());
        fragmentList.add(new SheHuiFragment());
        fragmentList.add(new GuoNeiFragment());
        fragmentList.add(new GuoJiFragment());
        fragmentList.add(new YuLeFragment());
        fragmentList.add(new TiYuFragment());
        fragmentList.add(new junShiFragment());
        fragmentList.add(new KeJiFragment());
        fragmentList.add(new CaiJingFragment());
        fragmentList.add(new ShiShangFragment());
        pager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
        pager.addOnPageChangeListener(new MyPagerChangedListen());

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_login) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


    class MyPagerChangedListen implements ViewPager.OnPageChangeListener {

        private int start;
        private int end;
        private int currentFragmentIndex;

        /**
         * viewpager在滑动时调用
         *
         * @param position
         * @param positionOffset       百分比 页面移动的百分比[0,1)
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //  Log.i("---滑动时position---",position+"");
            if (currentFragmentIndex == position) { //向右滑动
                end = tab_width * currentFragmentIndex +
                        (int) (tab_width * positionOffset);
            }
            if (currentFragmentIndex == position + 1) { //向左滑动
                end = tab_width * currentFragmentIndex -
                        (int) (tab_width * (1 - positionOffset));
            }
            //定义动画
            Animation mAnimation = new TranslateAnimation(start, end, 0, 0);
            mAnimation.setFillAfter(true); //使动画停留在终止的地方
            mAnimation.setDuration(0); //设置动画的时长
            imageMove.startAnimation(mAnimation); //开始动画
            start = end; //下次滑动的起始位置就是当前滑动的结束位置

        }

        /**
         * viewpager被选择后调用
         *
         * @param position
         */
        @Override
        public void onPageSelected(int position) {
//            Animation animation = new TranslateAnimation(,,0,0);
            //   Log.i("---被选择后position----",position+"");
            currentFragmentIndex = position;
            hsvMain.smoothScrollTo((currentFragmentIndex - 1) * tab_width, 0);
        }

        /**
         * viewpager状态改变时被调用
         *
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
