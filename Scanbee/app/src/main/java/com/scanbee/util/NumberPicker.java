package com.scanbee.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scanbee.scanbee.R;

/**
 * Created by Namra on 5/27/2016.
 */
public class NumberPicker {
    Context mContext;
    View viewMain;
    int initCount;
    // constructor
    public NumberPicker(Context context,View view,int count){
        this.mContext = context;
        this.viewMain = view;
        this.initCount = count;
    }

    @Nullable
    public void show() {
        final TextView numCount = (TextView) viewMain.findViewById(R.id.count);
        ImageView decrCount  = (ImageView) viewMain.findViewById(R.id.decrCount);
        ImageView incrCount = (ImageView) viewMain.findViewById(R.id.incrCount);
        numCount.setText(String.valueOf(initCount));
        incrCount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = Integer.valueOf(numCount.getText().toString());
                        numCount.setText(String.valueOf(count + 1));
                    }
                }
        );
        decrCount.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int count = Integer.valueOf(numCount.getText().toString());
                        if(count > 1)
                            numCount.setText(String.valueOf(count-1));
                    }
                }
        );
    }
    public int getCount(){
        TextView numCount = (TextView) viewMain.findViewById(R.id.count);
        return Integer.valueOf(numCount.getText().toString());
    };

}
