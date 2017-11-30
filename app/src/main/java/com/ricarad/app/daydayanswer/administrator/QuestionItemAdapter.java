package com.ricarad.app.daydayanswer.administrator;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import android.widget.TextView;

import com.ricarad.app.daydayanswer.R;
import com.ricarad.app.daydayanswer.moudle.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dongdong on 2017/11/28.
 */

public class QuestionItemAdapter extends ArrayAdapter {
    private int resourceId;
    private boolean isDel;  //是否显示删除复选框。显示：true；不显示：false
    private ArrayList<Question> delList;   //删除列表
    private Context context;

    public QuestionItemAdapter(Context context, int resource, List<Question> objects) {
        super(context, resource,objects);
        resourceId = resource;
        isDel = false;
        delList = new ArrayList<Question>();
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Question question = (Question) getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        }else {
            view = convertView;
        }
        TextView questionText = (TextView)view.findViewById(R.id.question_item_name);
        final CheckBox checkBox = (CheckBox)view.findViewById(R.id.question_checkbox);
        questionText.setText(question.getContent());

        if (isDel) {
            checkBox.setVisibility(View.VISIBLE);
        }else{
            checkBox.setChecked(false);
            checkBox.setVisibility(View.GONE);
        }
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBox.isChecked()){
                    delList.add(question);
                }else{
                    delList.remove(question);
                }
            }
        });
        return view;
    }




    public  void setDel(boolean isDel) {
        this.isDel = isDel;
        notifyDataSetChanged();//使系统调用getView()方法更新当前显示的列表项
    }

    public ArrayList<Question> getDelList() {
        return delList;
    }
}
