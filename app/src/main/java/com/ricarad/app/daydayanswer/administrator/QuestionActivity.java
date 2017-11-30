package com.ricarad.app.daydayanswer.administrator;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.ricarad.app.daydayanswer.R;

/**
 * Created by dongdong on 2017/11/29.
 */

public class QuestionActivity extends Activity {
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.question);

    }
}
