package com.example.clain.smasher;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;


public class GameView extends SurfaceView implements Runnable {
    //boolean variable to track if the game is playing or not
    volatile boolean playing;
    //the game thread
    private Thread gameThread = null;

    // Game is paused at the start
    boolean paused = true;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    // This variable tracks the game frame rate
    long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    // The size of the screen in pixels
    int screenX;
    int screenY;

    // The players paddle
    Platform platform;


    //Class constructor
    public GameView(Context context) {
        super(context);


        // Initialize ourHolder and paint objects
        surfaceHolder = getHolder();
        paint = new Paint();

        // Get a Display object to access screen details
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);

        screenX = size.x;
        screenY = size.y;

        platform = new Platform(screenX, screenY);

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

    }

    public void draw() {

        // Make sure our drawing surface is valid or we crash
        if (surfaceHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = surfaceHolder.lockCanvas();

            // Couleur de fond
            canvas.drawColor(Color.rgb(26, 128, 182));

            // choisir la couleur
            paint.setColor(Color.rgb(255, 255, 255));

            // Dessiner la plateforme
            canvas.drawRect(platform.getRect(), paint);

            surfaceHolder.unlockCanvasAndPost(canvas);

        }
    }
}