package com.ricarad.app.daydayanswer.normaluser;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.moudle.Question;

import java.util.ArrayList;

/**
 * Created by dongdong on 2017/11/30.
 */

public class GradeActivity extends Activity {
    private TextView rightnumberText;
    private TextView numbersText;
    private TextView scoreText;
    private Button OkButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.grade_view);
        OkButton = (Button)findViewById(R.id.yes_button);
        rightnumberText = (TextView)findViewById(R.id.rightnumber_text);
        numbersText = (TextView)findViewById(R.id.number_text);
        scoreText = (TextView)findViewById(R.id.score_text);
        ArrayList<Question> questionList = (ArrayList) getIntent().getStringArrayListExtra("questionList");
        ArrayList<String> answerList = (ArrayList)getIntent().getStringArrayListExtra("answerList");
        double score = 0;
        int rightIndex = 0;
        int questionNumber = questionList.size();
        for(int i = 0;i<questionList.size();i++){
            Question question = questionList.get(i);
            if(question.getResult().equals(answerList.get(i))){
                rightIndex++;
            }
        }
        if(questionNumber > 0){
            score = (rightIndex+0.0)/(questionNumber+0.0);
        }
        rightnumberText.setText("回答正确的题数："+rightIndex);
        numbersText.setText("总题数："+questionNumber);
        scoreText.setText("最终得分："+score);
        OkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
