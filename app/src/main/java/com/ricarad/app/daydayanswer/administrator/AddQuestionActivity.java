package com.ricarad.app.daydayanswer.administrator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.moudle.Question;

import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by dongdong on 2017/11/29.
 */

public class AddQuestionActivity extends Activity {
    private EditText questionEdit;
    private EditText AitemEdit;
    private EditText BitemEdit;
    private EditText CitemEdit;
    private EditText DitemEdit;
    private EditText resultEdit;
    private EditText analysisEdit;
    private Button backButton;
    private Button addButton;
    private boolean isUpdate = false;
    private  Question question = new Question();
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addquestion);
        questionEdit = (EditText)findViewById(R.id.addquestion_content_edit);
        AitemEdit = (EditText)findViewById(R.id.addAitem_edit);
        BitemEdit = (EditText)findViewById(R.id.addBitem_edit);
        CitemEdit = (EditText)findViewById(R.id.addCitem_edit);
        DitemEdit = (EditText)findViewById(R.id.addDitem_edit);
        resultEdit = (EditText)findViewById(R.id.addquestionresult_edit);
        analysisEdit = (EditText)findViewById(R.id.addquestionanalysis_edit);
        backButton = (Button)findViewById(R.id.addquestion_bnback);
        addButton = (Button)findViewById(R.id.addquestion_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isUpdate) {
                    final AlertDialog.Builder normalDialog =
                            new AlertDialog.Builder(AddQuestionActivity.this);
                    normalDialog.setMessage("确定要退出吗？");
                    normalDialog.setPositiveButton("确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                   finish();
                                }
                            });
                    normalDialog.setNegativeButton("关闭",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    // 显示
                    normalDialog.show();
                }else {
                    finish();
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //如果该题目还没有被添加过
                    isUpdate = true;
                    String content = questionEdit.getText().toString();
                    String Aitem = AitemEdit.getText().toString();
                    String Bitem = BitemEdit.getText().toString();
                    String Citem = CitemEdit.getText().toString();
                    String Ditem = DitemEdit.getText().toString();
                    String result = resultEdit.getText().toString();
                    String analysis = analysisEdit.getText().toString();
                    if(content.equals("") || result.equals("")){
                        Toast.makeText(AddQuestionActivity.this,"题目和答案不能为空",Toast.LENGTH_SHORT).show();
                    }else if(Aitem.equals("") && Bitem.equals("") && Citem.equals("") && Ditem.equals("")){
                        Toast.makeText(AddQuestionActivity.this,"必须至少有一个选项有答案",Toast.LENGTH_SHORT).show();
                    }else {
                        question.setContent(content);
                        question.setItemA(Aitem);
                        question.setItemB(Bitem);
                        question.setItemC(Citem);
                        question.setItemD(Ditem);
                        question.setResult(result);
                        question.setAnalysis(analysis);
                        question.save(AddQuestionActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(AddQuestionActivity.this,"添加题目成功",Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(AddQuestionActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
            }
        });
    }
}
