package com.example.clain.smasher;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.v7.widget.ButtonBarLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;


public class GameView extends SurfaceView implements Runnable {

    //the game thread
    Thread gameThread = null;

    // This is new. We need a SurfaceHolder
    SurfaceHolder surfaceHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;

    // Game is paused at the start
    boolean paused = true;

    // Game isGameOVer

    boolean isGameOver = false;
    boolean isWin = false;

    //These objects will be used for drawing
    Paint paint;
    Canvas canvas;

    // This variable tracks the game frame rate
    long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    // The size of the screen in pixels
    int screenX;
    int screenY;

    // The players paddle
    Platform platform;

    // A ball
    Ball ball;

    // Up to 200 bricks
    Brick[] bricks = new Brick[200];
    int numBricks = 0;


    int score=0;
    int highScore =0;

    //int highScore[] = new int[4];

    // sauvegarder des elements
    SharedPreferences sharedPreferences;

    int level=1;

    int IndRow = 3;

    int nextLevel;




    //Class constructor
    public GameView(Context context) {
        super(context);


        score = 0;


        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME",context.MODE_PRIVATE);

        highScore = sharedPreferences.getInt("Score1",0);



        // Initialize ourHolder and paint objects
        surfaceHolder = getHolder();
        surfaceHolder.setFixedSize(screenX, screenY/2);

        paint = new Paint();

        // Get a Display object to access screen details
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();


        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);


        screenX = size.x;
        screenY = size.y;

        platform = new Platform(screenX, screenY);

        // Create a ball
        ball = new Ball(screenX, screenY);

        createBricksAndRestart();


    }

    public void createBricksAndRestart() {

        // Put the ball back to the start
        ball.reset(screenX, screenY/2);

        int brickWidth = screenX / 8;
        int brickHeight = screenY / 10;

        // Build a wall of bricks

        numBricks = 0;


        if(isWin){
            IndRow++;
            for (int column = 0; column < 8; column++) {
                for (int row = 1; row < IndRow; row++) {
                    bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                    numBricks++;

                }

            }
            System.out.println("Nombre de bricks: "+numBricks);
            nextLevel = numBricks+score;

        }
        else{

            for (int column = 0; column < 8; column++) {
                for (int row = 1; row < IndRow; row++) {
                    bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                    numBricks++;
                }
            }

            nextLevel = numBricks;
        }

        /*for (int column = 0; column < 8; column++) {
            for (int row = 1; row < 3; row++) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
                numBricks++;
            }
        }*/
    }

    @Override
    public void run() {
        while (playing) {
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();
            // Update the frame
            if (!paused) {
                update();
            }
            //to draw the frame
            draw();
            // Calculate the fps this frame
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }
        }
    }


    public void update() {




        // Move the paddle if required
        platform.update(fps);

        ball.update(fps);

        // Check for ball colliding with a brick
        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].getVisibility()) {
                if (RectF.intersects(bricks[i].getRect(), ball.getRect())) {
                    System.out.println("BrickCollision");
                    bricks[i].setInvisible();
                    ball.reverseYVelocity();
                    score++;

                    if(score == nextLevel){

                        System.out.println("Win !");
                        isWin = true;
                        level++;
                        createBricksAndRestart();
                    }

                     /*if(level > 1){

                        if(score == numBricks){

                            System.out.println("Win !");
                            isWin = true;
                            level++;
                            createBricksAndRestart();
                        }
                    }
                    else{
                        if(score == numBricks){

                            System.out.println("Win !");
                            isWin = true;
                            level++;
                            createBricksAndRestart();
                        }
                    }*/


                    // Assigning the scores to the highscore integer array
                    /*for(int ind = 0;ind<5;ind++){
                        highScore[ind] = score;
                        break;
                    }
                    //storing the scores through shared Preferences
                    SharedPreferences.Editor e = sharedPreferences.edit();
                    for(int ind=0;ind <5;i++){
                        int j = ind+1;
                        e.putInt("score"+j,highScore[ind]);
                    }
                    e.apply();*/

                }
            }
        }

        // Check for ball colliding with paddle
        /*if (ball.getRect().bottom > platform.getRect().top) {
            System.out.println("Platform_Collision");
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(platform.getRect().top );
        }*/

        if(RectF.intersects(platform.getRect(),ball.getRect())){
            System.out.println("Platform_Collision");
            ball.setRandomXVelocity();
            ball.reverseYVelocity();
            ball.clearObstacleY(platform.getRect().top);
        }




        // Bounce the ball back when it hits the bottom of screen
        if (ball.getRect().bottom > screenY) {
            //ball.reverseYVelocity();
            //ball.clearObstacleY(screenY - 2);
            System.out.println("GameOver");
            playing = false;
            isGameOver = true;
            highScore = score;
            SharedPreferences.Editor e = sharedPreferences.edit();
            e.putInt("score1",highScore);
            e.apply();



        }

        // Bounce the ball back when it hits the top of screen
        if (ball.getRect().top < 0) {
            if (ball.y()<0){
                ball.reverseYVelocity();
                ball.clearObstacleY(10);
            }
        }

        // If the ball hits left wall bounce
        boolean collison;
        if (ball.getRect().left < 0) {
            ball.reverseXVelocity();
            ball.clearObstacleX(2);

        }
        // If the ball hits right wall bounce
        if (ball.getRect().right > screenX - 10) {
            ball.reverseXVelocity();
            ball.clearObstacleX(screenX - 40);


        }

        // platform out
        if(platform.getRect().right > screenX){
            platform.setMovementState(platform.LEFT);

        }
        if(platform.getRect().left <0){
            platform.setMovementState(platform.RIGHT);

        }


    }




    public void draw() {


        // Make sure our drawing surface is valid or we crash


        if (surfaceHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = surfaceHolder.lockCanvas();

            // Couleur de fond
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // choisir la couleur
            paint.setColor(Color.argb(255, 255, 255, 255));

            // Dessiner la plateforme
            canvas.drawRect(platform.getRect(), paint);

            // Draw the ball
            canvas.drawRect(ball.getRect(), paint);

            // Draw text
            paint.setColor(Color.argb(255, 0, 0, 0));
            paint.setTextSize(50);
            canvas.drawText("score: "+score,0,50,paint);

            paint.setColor(Color.argb(255, 0, 0, 0));
            paint.setTextSize(50);
            canvas.drawText("Level: "+level,screenX/2,50,paint);


            // Change the brush color for drawing
            paint.setColor(Color.argb(255, 249, 129, 0));


            // Draw the bricks if visible
            for(int i = 0; i < numBricks; i++){
                if(bricks[i].getVisibility()) {
                    canvas.drawRect(bricks[i].getRect(), paint);
                }
            }

            // Unlock the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);

        }

        if(isGameOver){
           DrawDefaite();

        }
    }

    public void DrawVictoire() {

        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            paint.setColor(Color.argb(255, 0, 0, 0));
            paint.setTextSize(50);
            canvas.drawText("Bravo: ",screenX/3,screenY/2,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    public  void  DrawDefaite(){
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            paint.setColor(Color.argb(255, 0, 0, 0));
            paint.setTextSize(50);
            canvas.drawText("GameOver !",screenX/3,screenY/2,paint);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;

        System.out.println("Le jeu est en pause");


        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }





    }

    // If SimpleGameEngine Activity is started theb
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);

        gameThread.start();

    }

    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            // Player has touched the screen

            case MotionEvent.ACTION_DOWN:
                paused = false;
                if (motionEvent.getX() > screenX / 2) {

                    platform.setMovementState(platform.RIGHT);
                } else

                {
                    platform.setMovementState(platform.LEFT);
                }

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:
                platform.setMovementState(platform.STOPPED);
                break;
        }

        return true;
    }

}