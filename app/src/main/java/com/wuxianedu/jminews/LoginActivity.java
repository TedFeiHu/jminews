package com.wuxianedu.jminews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.wuxianedu.jminews.bean.User;
import com.wuxianedu.jminews.utils.T;
import com.wuxianedu.jminews.utils.ValidUtil;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    private DbUtils db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        db = DbUtils.create(this);
        username = (EditText) findViewById(R.id.edit_text_username);
        password = (EditText) findViewById(R.id.edit_text_password);
        findViewById(R.id.button_login).setOnClickListener(this);
        findViewById(R.id.text_view_reg).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login: //登陆
                login();
                break;
            case R.id.text_view_reg: //注册
                Intent intent = new Intent(this, RegActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 登陆
     */
    private void login() {
        String usernameString = username.getText().toString();
        boolean flag = ValidUtil.isEmpty(usernameString);
        if (flag){
            T.showShort(this,"用户名不为空");
        }
        String passwordString = password.getText().toString();
        flag = ValidUtil.isEmpty(passwordString);
        if (flag){
            T.showShort(this,"密码不能为空");
        }

        //数据库中判断是否有用户名密码

        try {
            // select * from table where username = admin and password = 123456;
            //如果不存在 user = null;
            User user = db.findFirst(Selector.from(User.class).where("username","=",usernameString).and("password","=",passwordString));
            if (user == null){
                T.showShort(this, "用户名或密码错误");
            }else{
                T.showShort(this, "登陆成功");
                finish();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
