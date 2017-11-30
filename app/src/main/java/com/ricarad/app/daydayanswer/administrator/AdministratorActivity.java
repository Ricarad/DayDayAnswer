package com.ricarad.app.daydayanswer.administrator;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.activity.LoginActivity;
import com.ricarad.app.daydayanswer.moudle.Question;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;


/**
 * Created by dongdong on 2017/11/28.
 */

public class AdministratorActivity extends Activity implements PopupMenu.OnMenuItemClickListener  {
    private List<Question> questionList = new ArrayList<Question>();
    private QuestionItemAdapter questionItemAdapter;
    private ListView question_listview;
    private Button menuButton;
    private boolean isDelStat = false;
    private Button backButton;
    private String usercount;
    private String password;
    private String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.administrator);
        Bmob.initialize(this, "8e961ca4035d0b20d23273af6988e0c6");
        InitQuestion();
        Intent intent = getIntent();
        usercount = intent.getStringExtra("usrcount");
        password = intent.getStringExtra("password");
        username = intent.getStringExtra("username");

        menuButton = (Button)findViewById(R.id.top_bar_right_bn);
        backButton = (Button)findViewById(R.id.top_bar_back_bn);
        question_listview = (ListView) findViewById(R.id.question_list);
        question_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Question question = questionList.get(i);
                Intent intent = new Intent(AdministratorActivity.this,QuestionActivity.class);
                intent.putExtra("question",question);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDelStat){
                    Intent intent = new Intent();
                    intent.putExtra("usercount",usercount);
                    intent.putExtra("password",password);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else{
                    isDelStat = false;
                    questionItemAdapter.setDel(isDelStat);
                    menuButton.setText("更多");
                }
            }
        });
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDelStat){
                    showPopupMenu(view);
                }else {
                    ArrayList<Question> delList = questionItemAdapter.getDelList();
                    if(delList.size()>0){
                        for (Question question : delList){
                            question.delete(AdministratorActivity.this);
                            questionList.remove(question);
                        }
                    }
                    isDelStat = false;
                    questionItemAdapter.setDel(isDelStat);
                    menuButton.setText("更多");
                }
            }
        });
    }

    private void showPopupMenu(View view){
        PopupMenu popup = new PopupMenu(this,view);
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.questionbook, popup.getMenu());
        //绑定菜单项的点击事件
        popup.setOnMenuItemClickListener(AdministratorActivity.this);
        //显示弹出菜单
        popup.show();
    }

    public void InitQuestion(){
        BmobQuery<Question> query = new BmobQuery<Question>();
        query.findObjects(AdministratorActivity.this, new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> list) {
                if(list.size() != 0){
                    for(Question question : list){
                        questionList.add(question);
                    }
                    questionItemAdapter = new QuestionItemAdapter(AdministratorActivity.this,R.layout.list_item,questionList);
                    question_listview.setAdapter(questionItemAdapter);
                    questionItemAdapter.notifyDataSetChanged();
                }

                Toast.makeText(AdministratorActivity.this,"查询问题表成功"+list.size(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(int i, String s) {
                Toast.makeText(AdministratorActivity.this,"查询问题失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.add_question_item:{
                Intent intent = new Intent(AdministratorActivity.this,AddQuestionActivity.class);
                startActivity(intent);
            }break;
            case R.id.del_question_item:{
                isDelStat = true;
                questionItemAdapter.setDel(isDelStat);
                menuButton.setText("完成");
            }break;
        }
        return true;
    }
}
