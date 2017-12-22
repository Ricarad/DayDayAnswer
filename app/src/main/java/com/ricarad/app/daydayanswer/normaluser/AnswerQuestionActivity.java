package com.ricarad.app.daydayanswer.normaluser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.administrator.QuestionActivity;
import com.ricarad.app.daydayanswer.moudle.Question;
import com.ricarad.app.daydayanswer.moudle.User;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by dongdong on 2017/11/30.
 */

public class AnswerQuestionActivity extends Activity implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {
    private RadioGroup radioSelect;
    private Button back;
    private Button menuButton;
    private Button perviousButton;
    private Button nextButton;
    private TextView questioncontent;
    private TextView Aitem;
    private TextView Bitem;
    private TextView Citem;
    private TextView Ditem;
    private TextView resultText;
    private TextView analysisText;

    private ArrayList<Question> questionList = new ArrayList<Question>(); //题目集合
    private ArrayList<String> answerList = new ArrayList<String>();
    private int currentIndex = 0;
    private boolean isShowResult = true;

    private ImageView headPic;
    private TextView nameText;
    private ListView questionSlideList;
    private ArrayList<String> titleList = new ArrayList<String>();
    private SlideListAdapter slideadapter;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.answer_view);
        user = (User)getIntent().getSerializableExtra("user");
        Bmob.initialize(this, "8e961ca4035d0b20d23273af6988e0c6");



        //设置SlideMenu功能
        SlidingMenu slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
       // slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setShadowWidthRes(R.dimen.shadow_width);
        //SlidingMenu划出时主页面显示的剩余宽度
        //slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        //设置SlidingMenu菜单的宽度
        slidingMenu.setBehindWidthRes(R.dimen.slidingmenu_offset_large);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        slidingMenu.attachToActivity(AnswerQuestionActivity.this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        slidingMenu.setMenu(R.layout.question_slide_view);
        //View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.question_slide_view, null);
        View view = slidingMenu.getMenu();
        questionSlideList = (ListView)view.findViewById(R.id.questionlist_slide_view);
        nameText = (TextView)view.findViewById(R.id.slide_name_text);
        headPic = (ImageView)view.findViewById(R.id.head_pic);
        nameText.setText(user.getUsername());


        radioSelect = (RadioGroup)findViewById(R.id.select_radiogroup);
        back = (Button)findViewById(R.id.top_bar_back_bn);
        menuButton = (Button)findViewById(R.id.top_bar_right_bn);
        perviousButton = (Button)findViewById(R.id.pervious_button);
        nextButton = (Button)findViewById(R.id.next_button);
        questioncontent = (TextView)findViewById(R.id.question_text);
        Aitem = (TextView)findViewById(R.id.Aitem_text);
        Bitem = (TextView)findViewById(R.id.Bitem_text);
        Citem = (TextView)findViewById(R.id.Citem_text);
        Ditem = (TextView)findViewById(R.id.Ditem_text);
        resultText = (TextView)findViewById(R.id.result_text);
        analysisText = (TextView)findViewById(R.id.analysis_text);
        resultText.setVisibility(View.INVISIBLE);
        analysisText.setVisibility(View.INVISIBLE);
        back.setOnClickListener(this);
        menuButton.setOnClickListener(this);
        perviousButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        InitQuestion();

        questionSlideList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectItem = "";
                for(int m = 0;m<radioSelect.getChildCount();m++){
                    RadioButton bt = (RadioButton)radioSelect.getChildAt(m);
                    if(bt.isChecked()){
                        selectItem = bt.getText().toString();
                        break;
                    }
                }
                answerList.set(currentIndex,selectItem);

                for(int j = 0;j<questionList.size();j++){
                    View view1 = questionSlideList.getChildAt(j);
                    TextView textView = (TextView)view1.findViewById(R.id.slide_listitem_text);
                    if(i!=j){
                        textView.setBackgroundResource(R.drawable.yellow_bg1);
                    }else {
                        textView.setBackgroundResource(R.drawable.yellow_bg);
                    }
                }
                Question question = questionList.get(i);
                setView(i,question);
                currentIndex = i;
                String NselectItem = answerList.get(currentIndex);
                if(NselectItem.equals("")) {
                    for (int j = 0; j < radioSelect.getChildCount(); j++) {
                        RadioButton bt = (RadioButton) radioSelect.getChildAt(j);
                        bt.setChecked(false);
                    }
                }else {
                    for (int j = 0; j < radioSelect.getChildCount(); j++) {
                        RadioButton bt = (RadioButton) radioSelect.getChildAt(j);
                        if(bt.getText().equals(NselectItem)){
                            bt.setChecked(true);
                            break;
                        }
                    }
                }
            }
        });

    }
    public void setView(int i,Question question){
        questioncontent.setText(i+"."+question.getContent());
        Aitem.setText("A."+question.getItemA());
        Bitem.setText("B."+question.getItemB());
        Citem.setText("C."+question.getItemC());
        Ditem.setText("D."+question.getItemD());
        resultText.setText("正确答案为："+question.getResult());
        analysisText.setText("问题解析："+question.getAnalysis());
    }

    public void InitQuestion(){
        BmobQuery<Question> query = new BmobQuery<Question>();
        query.findObjects(AnswerQuestionActivity.this, new FindListener<Question>() {
            @Override
            public void onSuccess(List<Question> list) {
                if(list.size() != 0){
                    int i = 1;
                    for(Question question : list){
                        questionList.add(question);
                        answerList.add("");
                        titleList.add(i+"."+question.getContent());
                        i++;
                    }
                    slideadapter = new SlideListAdapter(AnswerQuestionActivity.this,R.layout.slidelistitem,titleList);
                    questionSlideList.setAdapter(slideadapter);
                    slideadapter.notifyDataSetChanged();
                    setView(currentIndex,questionList.get(0));

                }
            }
            @Override
            public void onError(int i, String s) {
                Toast.makeText(AnswerQuestionActivity.this,"加载问题失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopupMenu(View view){
        PopupMenu popup = new PopupMenu(this,view);
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.answerquestion, popup.getMenu());
        //绑定菜单项的点击事件
        popup.setOnMenuItemClickListener(AnswerQuestionActivity.this);
        //显示弹出菜单
        popup.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.next_button:{
                String selectItem = "";
                for(int i = 0;i<radioSelect.getChildCount();i++){
                    RadioButton bt = (RadioButton)radioSelect.getChildAt(i);
                    if(bt.isChecked()){
                        selectItem = bt.getText().toString();
                        break;
                    }
                }
                answerList.set(currentIndex,selectItem);
                //保存上一题的选项之后，显示下一题，将Radiogroup设置为0
                currentIndex++;
                if(currentIndex < questionList.size()) {
                    //这一题的选项背景设为淡黄色
                    View view1 = questionSlideList.getChildAt(currentIndex);
                    TextView textView1 = (TextView)view1.findViewById(R.id.slide_listitem_text);
                    textView1.setBackgroundResource(R.drawable.yellow_bg);
                    //上一题的选项背景设为橘黄色
                    View view2 = questionSlideList.getChildAt(currentIndex-1);
                    TextView textView2 = (TextView)view2.findViewById(R.id.slide_listitem_text);
                    textView2.setBackgroundResource(R.drawable.yellow_bg1);

                    Question question = questionList.get(currentIndex);
                    setView(currentIndex, question);
                    String NselectItem = answerList.get(currentIndex);
                    if(NselectItem.equals("")) {
                        for (int i = 0; i < radioSelect.getChildCount(); i++) {
                            RadioButton bt = (RadioButton) radioSelect.getChildAt(i);
                                bt.setChecked(false);
                        }
                    }else {
                        for (int i = 0; i < radioSelect.getChildCount(); i++) {
                            RadioButton bt = (RadioButton) radioSelect.getChildAt(i);
                            if(bt.getText().equals(NselectItem)){
                                bt.setChecked(true);
                                break;
                            }
                        }
                    }
                }else{
                    currentIndex--;
                    Toast.makeText(AnswerQuestionActivity.this,"当前已是最后一题",Toast.LENGTH_SHORT).show();
                }
            }break;
            case R.id.pervious_button:{
                String selectItem = "";
                for(int i = 0;i<radioSelect.getChildCount();i++){
                    RadioButton bt = (RadioButton)radioSelect.getChildAt(i);
                    if(bt.isChecked()){
                        selectItem = bt.getText().toString();
                        break;
                    }
                }
                answerList.set(currentIndex,selectItem);

                //保存这一题的选项之后，显示上一题，将Radiogroup设置为上一题的选项
                currentIndex--;
                if(currentIndex >=0){
                    //这一题的选项背景设为淡黄色
                    View view1 = questionSlideList.getChildAt(currentIndex);
                    TextView textView1 = (TextView)view1.findViewById(R.id.slide_listitem_text);
                    textView1.setBackgroundResource(R.drawable.yellow_bg);
                    //上一题的选项背景设为橘黄色
                    View view2 = questionSlideList.getChildAt(currentIndex+1);
                    TextView textView2 = (TextView)view2.findViewById(R.id.slide_listitem_text);
                    textView2.setBackgroundResource(R.drawable.yellow_bg1);

                    Question question = questionList.get(currentIndex);
                    setView(currentIndex, question);
                    String PselectItem = answerList.get(currentIndex);
                    if(PselectItem.equals("")) {
                        for (int i = 0; i < radioSelect.getChildCount(); i++) {
                            RadioButton bt = (RadioButton) radioSelect.getChildAt(i);
                            bt.setChecked(false);
                        }
                    }else {
                        for (int i = 0; i < radioSelect.getChildCount(); i++) {
                            RadioButton bt = (RadioButton) radioSelect.getChildAt(i);
                            if(bt.getText().equals(PselectItem)){
                                bt.setChecked(true);
                                break;
                            }
                        }
                    }
                }else {
                    currentIndex++;
                    Toast.makeText(AnswerQuestionActivity.this,"当前已是第一题",Toast.LENGTH_SHORT).show();
                }
            }break;
            case R.id.top_bar_back_bn:{
                finish();
            }break;
            case R.id.top_bar_right_bn:{
                showPopupMenu(view);
            }break;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.showresult:{
              if(!isShowResult){
                    resultText.setVisibility(View.INVISIBLE);
                  analysisText.setVisibility(View.INVISIBLE);
                  isShowResult = true;
              }else {
                    resultText.setVisibility(View.VISIBLE);
                   analysisText.setVisibility(View.VISIBLE);
                  isShowResult = false;
              }
            }break;
            case R.id.quit_answer:{
                Intent intent = new Intent(AnswerQuestionActivity.this,GradeActivity.class);
                intent.putExtra("questionList",questionList);
                intent.putExtra("answerList",answerList);
                startActivity(intent);
                finish();
            }break;
        }
        return true;
    }
}
