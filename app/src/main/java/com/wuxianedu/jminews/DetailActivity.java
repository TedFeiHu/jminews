package com.wuxianedu.jminews;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Hu131 on 2016/6/29.
 */
public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String uri = intent.getStringExtra("uri");
        WebView web = (WebView) findViewById(R.id.web_view_detail);
        web.loadUrl(uri);
    }
}
