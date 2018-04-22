package com.example.clain.smasher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CLAIN on 17/04/2018.
 */

public class Score extends AppCompatActivity {

    ListView ScoreList;
    int CheckScore;

    //String[] score = new String[] {"101","145","20000","216"};
    List<String> score = new ArrayList<String>();

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ScoreList = findViewById(R.id.listView);
        sharedPreferences = getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);




        score.add(String.valueOf(sharedPreferences.getInt("score1",0)));



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Score.this,
                android.R.layout.simple_list_item_1, score);
        ScoreList.setAdapter(adapter);


    }
}
