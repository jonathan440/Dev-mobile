package com.example.clain.smasher;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //image button
    private Button buttonPlay;
    private  Button buttonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the orientation to landscape
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //getting the button
        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonScore=(Button) findViewById(R.id.buttonScore);

        //adding a click listener
        buttonPlay.setOnClickListener(this);
        buttonScore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.buttonPlay:
                //starting game activity
                startActivity(new Intent(this, GameActivity.class));

                break;

            case R.id.buttonScore:
                System.out.println("Score");
                startActivity(new Intent(this, Score.class));

                break;

            default:
                break;

        }



}


}