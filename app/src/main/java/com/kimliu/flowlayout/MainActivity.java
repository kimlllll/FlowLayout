package com.kimliu.flowlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FlowLayout mFlowLayout;
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFlowLayout = findViewById(R.id.flowlayout);


        list.add("水果味绿茶");
        list.add("水果味");
        list.add("绿茶");
        list.add("水果");
        list.add("茶");
        list.add("绿茶");
        list.add("水果");
        list.add("绿茶");
        list.add("水果味绿茶");
        list.add("水果味");
        list.add("茶");
        list.add("水果味绿茶");
        list.add("水果");
        list.add("绿茶");
        list.add("水果味绿茶");


        for(int i = 0; i< list.size();i++){
            TextView textView = new TextView(this);
            textView.setPadding(10,10,10,10);
            textView.setBackgroundColor(Color.BLUE);
            textView.setText(list.get(i));
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(20);
            mFlowLayout.addView(textView);
        }

    }
}