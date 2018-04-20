package com.example.clain.smasher;

import android.graphics.RectF;

public class Platform {

    private RectF rect;

    // longueur et hauteur
    public float length;
    public float height;

    // X is the far left of the rectangle which forms our paddle
    private float x;

    // Y is the top coordinate
    private float y;

    // This will hold the pixels per second speed that the paddle will move
    private float platformSpeed;

    // Direction des mouvements de la plateforme
    public final int STOPPED = 0;
    public final int LEFT = 1;
    public final int RIGHT = 2;

    private int platformMoving = STOPPED;

    // This the the constructor method
    // When we create an object from this class we will pass
    // in the screen width and height
    public Platform(int screenX, int screenY){
        // 130 pixels wide and 20 pixels high
        length = 130;
        height = 20;

        // Start paddle in roughly the sceen centre
        x = screenX / 2;
        y = screenY - 150;

        rect = new RectF(x, y, x + length, y + height);



        // How fast is the paddle in pixels per second
        platformSpeed = 500;
    }

    // This is a getter method to make the rectangle that
    // defines our paddle available in BreakoutView class
    public RectF getRect(){return rect;}



    // This method will be used to change/set if the paddle is going left, right or nowhere
    public void setMovementState(int state){
        platformMoving = state;
    }

    // Mouvement plateforme et changement de coordonnees
    public void update(long fps){
        if(platformMoving == LEFT){
            x = x - platformSpeed / fps;
        }

        if(platformMoving == RIGHT){
            x = x + platformSpeed / fps;
        }

        rect.left = x;
        rect.right = x + length;
    }

}