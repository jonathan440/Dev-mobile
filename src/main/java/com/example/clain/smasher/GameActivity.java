package com.example.clain.smasher;


        import android.provider.Settings;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    //declarer gameview
    private GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.score);

        //Initialisation de gameview
        gameView = new GameView(this);

        //adding it to contentview



        setContentView(gameView);




    }

    // This method executes when the player starts the game
    @Override
    protected void onResume() {
        super.onResume();
        // Tell the gameView resume method to execute

        gameView.resume();


    }

    // This method executes when the player quits the game
    @Override
    protected void onPause() {
        super.onPause();
        // Tell the gameView pause method to execute
        gameView.pause();
        System.out.println("pause");
    }


}