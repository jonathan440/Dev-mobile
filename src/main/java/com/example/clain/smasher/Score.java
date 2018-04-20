package com.example.clain.smasher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by CLAIN on 17/04/2018.
 */

public class Score extends AppCompatActivity {

    ListView ScoreList;
    String[] score = new String[] {"101","145","20000","216"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ScoreList = findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Score.this,
                android.R.layout.simple_list_item_1, score);
        ScoreList.setAdapter(adapter);


    }
}
