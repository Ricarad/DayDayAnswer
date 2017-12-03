package com.ricarad.app.daydayanswer.normaluser;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ricarad.app.daydayanswer.R;

import java.util.List;

/**
 * Created by dongdong on 2017/12/2.
 */

public class SlideListAdapter extends ArrayAdapter {
    private int resourceId;
    public SlideListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        resourceId = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String title = (String) getItem(position);
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else {
            view = convertView;
        }
        TextView textView = (TextView)view.findViewById(R.id.slide_listitem_text);
        textView.setText(title);
        return view;
    }
}
