package com.example.clain.smasher;



import android.graphics.RectF;

import java.util.Random;

public class Ball {
    private RectF rect;
    private float xVelocity;
    private float yVelocity;
    private float ballWidth = 20;
    private float ballHeight = 20;
    // X is the far left of the rectangle which forms our paddle
    private float x;

    // Y is the top coordinate
    private float y;

    public Ball(int screenX, int screenY){

        x = screenX / 2;
        y = screenY - 60;

        // Start the ball travelling straight up at 100 pixels per second
        xVelocity = 100;
        yVelocity = -400;

        // Place the ball in the centre of the screen at the bottom
        // Make it a 10 pixel x 10 pixel square
        //rect = new RectF(x, y, x + ballWidth, y + ballHeight);
        rect = new RectF(x, y, x+ballWidth, y+ballHeight);

    }
    public float x() {return xVelocity;}
    public float y() {return yVelocity;}

    public RectF getRect(){
        return rect;
    }

    public void update(long fps){
        rect.left = rect.left + (xVelocity / fps);
        rect.top = rect.top + (yVelocity / fps);
        rect.right = rect.left + ballWidth;
        rect.bottom = rect.top - ballHeight;
    }

    public void reverseYVelocity(){
        yVelocity = -yVelocity;
    }

    public void reverseXVelocity(){
        xVelocity = - xVelocity;
    }

    public void setRandomXVelocity(){
        Random generator = new Random();
        int answer = generator.nextInt(2);

        if(answer == 0){
            reverseXVelocity();
        }
    }

    public void clearObstacleY(float y){
        rect.bottom = y;
        rect.top = y - ballHeight;
    }

    public void clearObstacleX(float x){
        rect.left = x;
        rect.right = x + ballWidth;
    }

    public void reset(int x, int y){
        rect.left = x / 2;
        rect.top = y - 60;
        rect.right = x / 2 + ballWidth;
        rect.bottom = y - 60 - ballHeight;
    }

}