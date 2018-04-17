package com.example.clain.smasher;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;


public class GameView extends SurfaceView implements Runnable {
    public static DisplayMetrics screenSize;
    //the game thread
    Thread gameThread = null;

    // This is new. We need a SurfaceHolder
    SurfaceHolder surfaceHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    volatile boolean playing;

    // Game is paused at the start
    boolean paused = true;

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


    // Brick



    Brick brick;

    //Class constructor
    public GameView(Context context) {
        super(context);


        // Initialize ourHolder and paint objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // Get a Display object to access screen details

        DisplayMetrics display = (DisplayMetrics) ((Activity) getContext()).getWindowManager();


        // Load the resolution into a Point object


        Point size = new Point();


        display.getSize(size);








        screenX = size.x;
        screenY = size.y;

        platform = new Platform(screenX, screenY);

        // Create a ball
        ball = new Ball(screenX, screenY);

        // Create brick

        brick = new Brick();




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

            // Change the brush color for drawing
            paint.setColor(Color.argb(255, 249, 129, 0));

            // Unlock the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }

    // If SimpleGameEngine Activity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
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