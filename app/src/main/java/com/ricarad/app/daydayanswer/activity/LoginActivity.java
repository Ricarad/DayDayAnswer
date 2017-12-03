package com.ricarad.app.daydayanswer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.administrator.AdministratorActivity;
import com.ricarad.app.daydayanswer.moudle.Question;
import com.ricarad.app.daydayanswer.moudle.User;
import com.ricarad.app.daydayanswer.normaluser.NormalUserActivity;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dongdong on 2017/11/28.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    private Button loginButton;
    private Button registerButton;
    private EditText usercountText;
    private EditText passwordText;
    private final static int REGISTER_CODE = 1;
    private final static int ADMINISTRATOR_CODE = 1;
    private final static int NORMALUSER_CODE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        Bmob.initialize(this, "8e961ca4035d0b20d23273af6988e0c6");

        loginButton = (Button)findViewById(R.id.login_button);
        registerButton = (Button)findViewById(R.id.register_button);
        usercountText = (EditText)findViewById(R.id.usrcount_text);
        passwordText = (EditText)findViewById(R.id.password_text);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       switch (requestCode){
           case REGISTER_CODE:{
               if(resultCode == RESULT_OK){
                   usercountText.setText(data.getStringExtra("usercount"));
                   passwordText.setText(data.getStringExtra("password"));
               }
           }break;

       }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.login_button:{
                String usercount = usercountText.getText().toString();
                String password = passwordText.getText().toString();
                if(usercount.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    login(usercount,password);
                }
            }break;
            case R.id.register_button:{
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,REGISTER_CODE);
            }break;
        }
    }

    public void login(String usercount,String password){
        BmobQuery<User> query = new BmobQuery<User>();
        query.addWhereEqualTo("usercount",usercount);
        query.addWhereEqualTo("password",password);
        query.setLimit(1);
        query.findObjects(LoginActivity.this, new FindListener<User>() {
            @Override

            public void onSuccess(List<User> list) {
                if(list != null && list.size() !=0) {
                    User user = list.get(0);
                    String role = user.getRole();
                    if(role.equals("管理员")){
                        Intent intent = new Intent(LoginActivity.this,AdministratorActivity.class);
                        intent.putExtra("user",user);
                        startActivityForResult(intent,ADMINISTRATOR_CODE);

                    }else if(role.equals("用户")){
                        Intent intent = new Intent(LoginActivity.this,NormalUserActivity.class);
                        intent.putExtra("user",user);
                        startActivityForResult(intent,NORMALUSER_CODE);
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"用户名或密码不正确",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
