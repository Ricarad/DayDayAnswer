package com.ricarad.app.daydayanswer.normaluser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.moudle.User;

/**
 * Created by dongdong on 2017/11/28.
 */

public class NormalUserActivity extends Activity {
    private Button start;
    private Button loginout;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.normaluser);

        Intent intent = getIntent();
        user = (User)intent.getSerializableExtra("user");

        start = (Button)findViewById(R.id.start_answer);
        loginout = (Button)findViewById(R.id.loginout);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/幼圆.ttf");
        start.setTypeface(typeFace);
        loginout.setTypeface(typeFace);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NormalUserActivity.this,AnswerQuestionActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("usercount",user.getUsercount());
                intent.putExtra("password",user.getPassword());
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
