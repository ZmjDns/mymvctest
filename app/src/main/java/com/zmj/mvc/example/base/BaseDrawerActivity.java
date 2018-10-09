package com.zmj.mvc.example.base;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.zmj.mvc.example.Config;
import com.zmj.mvc.example.R;

/**
 * @author Zmj
 * @date 2018/9/28
 */
public abstract class BaseDrawerActivity extends BaseActivity {

    protected DrawerLayout rootLayout;
    protected FrameLayout flActivityContiner;

    NavigationView.OnNavigationItemSelectedListener mListner = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_git_hub:
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Config.GITHUB)));
                    break;
                case R.id.action_blog:
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(Config.BLOG)));
                    break;
            }
            return false;
        }
    };

    @Override
    protected void setBaseView(@LayoutRes int layoutId) {
        mContentView = LayoutInflater.from(this).inflate(R.layout.act_drawer,null);
        setContentView(mContentView);
        rootLayout = findViewById(R.id.root_layout);
        flActivityContiner = findViewById(R.id.activity_container);
        flActivityContiner.addView(LayoutInflater.from(this).inflate(layoutId,flActivityContiner,false));
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mListner);
    }
}
