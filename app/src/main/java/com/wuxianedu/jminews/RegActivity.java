package com.wuxianedu.jminews;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.wuxianedu.jminews.bean.User;
import com.wuxianedu.jminews.utils.T;
import com.wuxianedu.jminews.utils.ValidUtil;


/**
 * Created by Hu131 on 2016/6/21.
 */
public class RegActivity extends AppCompatActivity {

    private EditText usernameReg;
    private EditText passwordReg;
    private EditText surePass;
    private DbUtils db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initView();
    }

    private void initView() {
        db = DbUtils.create(this);
        usernameReg = (EditText) findViewById(R.id.edit_text_reg_username);
        passwordReg = (EditText) findViewById(R.id.edit_text_reg_password);
        surePass = (EditText) findViewById(R.id.edit_text_reg_sure);
    }

    /**
     * 注册按钮的点击事件
     * @param view
     */
    public void reg(View view) {

        String username = usernameReg.getText().toString();
        String password = passwordReg.getText().toString();
        if (ValidUtil.isEmpty(username)||
                ValidUtil.isEmpty(password)||
                ValidUtil.isEmpty(surePass.getText().toString())){
            T.showShort(this ,"用户名密码不能为空");
            return;
        }

        try {
         User user = db.findFirst(Selector.from(User.class).where("username", "= ", usernameReg));
            if (user!=null){
                    T.showShort(this,"用户名已存在");
                    return;

            }else {
                User u = new User();
                u.setUsername(username);
                u.setPassword(password);
                db.save(u);
                T.showShort(this,"success");
            }


        } catch (DbException e) {
            e.printStackTrace();
        }

    }
}
