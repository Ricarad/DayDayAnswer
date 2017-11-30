package com.ricarad.app.daydayanswer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.moudle.User;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dongdong on 2017/11/28.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{
    private Button registerButton;
    private Button backButton;
    private EditText usercountText;
    private EditText passwordText;
    private RadioGroup roleRadio;
    private EditText usernameText;
    private String role = "用户";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);
        Bmob.initialize(this, "8e961ca4035d0b20d23273af6988e0c6");

        registerButton = (Button)findViewById(R.id.registerok_button);
        backButton = (Button)findViewById(R.id.back_button);
        usercountText = (EditText)findViewById(R.id.registercount_text);
        passwordText = (EditText)findViewById(R.id.registerpassword_text);
        roleRadio = (RadioGroup) findViewById(R.id.role_radiogroup);
        usernameText = (EditText)findViewById(R.id.registerusername_text);
        backButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

       roleRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
               if(id == R.id.user_radio){
                   role = "用户";
               }else if(id == R.id.administrator_radio){
                   role = "管理员";
               }
           }
       });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_button:{
                finish();
            }break;
            case R.id.registerok_button:{
                final String usercount = usercountText.getText().toString();
                String username = usernameText.getText().toString();
                final String password = passwordText.getText().toString();
                final User user = new User();
                user.setPassword(password);
                user.setUsercount(usercount);
                user.setUsername(username);
                user.setRole(role);
                BmobQuery<User> query = new BmobQuery<User>();
                query.addWhereEqualTo("usercount",usercount);
                query.setLimit(1);
                query.findObjects(RegisterActivity.this, new FindListener<User>() {
                    @Override
                    public void onSuccess(List<User> list) {
                        if(list.size() == 0){
                            user.save(RegisterActivity.this);
                            Intent intent = new Intent();
                            intent.putExtra("usercount",usercount);
                            intent.putExtra("password",password);
                            setResult(RESULT_OK,intent);
                            finish();
                        }else {
                            Toast.makeText(RegisterActivity.this,"用户账号已存在",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
            }break;
        }
    }



}
