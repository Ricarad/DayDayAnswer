package com.ricarad.app.daydayanswer.administrator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.moudle.Question;

import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by dongdong on 2017/11/29.
 */

public class QuestionActivity extends Activity implements View.OnClickListener{
    private EditText questionEdit;
    private EditText AitemEdit;
    private EditText BitemEdit;
    private EditText CitemEdit;
    private EditText DitemEdit;
    private EditText resultEdit;
    private EditText analysisEdit;
    private Button backButton;
    private Button saveButton;
    private Button editButton;
    private boolean isEdit = false;
    private Question question;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.question);
        Intent intent = getIntent();
        question = (Question) intent.getSerializableExtra("question");

        questionEdit = (EditText)findViewById(R.id.question_content_edit);
        AitemEdit = (EditText)findViewById(R.id.Aitem_edit);
        BitemEdit = (EditText)findViewById(R.id.Bitem_edit);
        CitemEdit = (EditText)findViewById(R.id.Citem_edit);
        DitemEdit = (EditText)findViewById(R.id.Ditem_edit);
        resultEdit = (EditText)findViewById(R.id.questionresult_edit);
        analysisEdit = (EditText)findViewById(R.id.questionanalysis_edit);
        backButton = (Button)findViewById(R.id.questioninfo_bnback);
        saveButton = (Button)findViewById(R.id.savequestion_button);
        editButton = (Button)findViewById(R.id.editquestion_button);
        saveButton.setEnabled(false);
        backButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        editButton.setOnClickListener(this);
        if(question != null){
            InitQueston();
        }else {
            Toast.makeText(this,"加载题目失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.editquestion_button:{
                isEdit = true;
                editButton.setEnabled(false);
                saveButton.setEnabled(true);
                Editable();
            }break;
            case R.id.savequestion_button:{
                isEdit = false;
                editButton.setEnabled(true);
                saveButton.setEnabled(false);
                Editenable();
                String content = questionEdit.getText().toString();
                String Aitem = AitemEdit.getText().toString();
                String Bitem = BitemEdit.getText().toString();
                String Citem = CitemEdit.getText().toString();
                String Ditem = DitemEdit.getText().toString();
                String result = resultEdit.getText().toString();
                String analysis = analysisEdit.getText().toString();
                question.setContent(content);
                question.setItemA(Aitem);
                question.setItemB(Bitem);
                question.setItemC(Citem);
                question.setItemD(Ditem);
                question.setResult(result);
                question.setAnalysis(analysis);
                question.update(QuestionActivity.this, new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(QuestionActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(QuestionActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }break;
            case R.id.questioninfo_bnback:{
                if (isEdit){
                    isEdit = false;
                    Editenable();
                    editButton.setEnabled(true);
                    saveButton.setEnabled(false);
                }else {
                    finish();
                }
            }break;
        }
    }


    public void InitQueston(){
        questionEdit.setText(question.getContent());
        AitemEdit.setText(question.getItemA());
        BitemEdit.setText(question.getItemB());
        CitemEdit.setText(question.getItemC());
        DitemEdit.setText(question.getItemD());
        resultEdit.setText(question.getResult());
        analysisEdit.setText(question.getAnalysis());
    }
    public void Editable(){
        questionEdit.setFocusable(true);
        questionEdit.setFocusableInTouchMode(true);
        AitemEdit.setFocusable(true);
        AitemEdit.setFocusableInTouchMode(true);
        BitemEdit.setFocusable(true);
        BitemEdit.setFocusableInTouchMode(true);
        CitemEdit.setFocusable(true);
        CitemEdit.setFocusableInTouchMode(true);
        DitemEdit.setFocusable(true);
        DitemEdit.setFocusableInTouchMode(true);
        resultEdit.setFocusable(true);
        resultEdit.setFocusableInTouchMode(true);
        analysisEdit.setFocusable(true);
        analysisEdit.setFocusableInTouchMode(true);
        Toast.makeText(QuestionActivity.this,"开启编辑模式",Toast.LENGTH_SHORT).show();
    }
    public void Editenable(){
        questionEdit.setFocusable(false);
        questionEdit.setFocusableInTouchMode(false);
        AitemEdit.setFocusable(false);
        AitemEdit.setFocusableInTouchMode(false);
        BitemEdit.setFocusable(false);
        BitemEdit.setFocusableInTouchMode(false);
        CitemEdit.setFocusable(false);
        CitemEdit.setFocusableInTouchMode(false);
        DitemEdit.setFocusable(false);
        DitemEdit.setFocusableInTouchMode(false);
        resultEdit.setFocusable(false);
        resultEdit.setFocusableInTouchMode(false);
        analysisEdit.setFocusable(false);
        analysisEdit.setFocusableInTouchMode(false);
    }
}
