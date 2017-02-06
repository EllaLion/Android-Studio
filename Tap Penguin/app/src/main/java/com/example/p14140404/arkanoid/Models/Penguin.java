package com.example.p14140404.arkanoid.Models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;

import com.example.p14140404.arkanoid.R;

/**
 * Created by P14140404 on 25/04/2016.
 */
public class Penguin {
    private int iX;
    private int iY;
    private int iWidth;
    private int iHeight;
    private Drawable penguin;

    private int jump = -50;
    private int grav = 5;
    private int change = 0;
    private int floor;



    public Penguin()
    {

    }

    public Penguin(int x, int y, int width, int height, Context context)
    {
        iX = x;
        iY = y;
        floor = iY;
        iWidth = width;
        iHeight = height;
        penguin = ContextCompat.getDrawable(context, R.drawable.mainpenguin);
    }

    public void draw(Canvas canvas)
    {
        penguin.setBounds(iX, iY, iX + iWidth, iY + iHeight);
        penguin.draw(canvas);
    }

    public void update()
    {
        iY += change;
        change += grav;
        if (change > 0 && iY > floor) {
            change = 0;
            iY = floor;
        }
    }

    public void jump(Context context)
    {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.boing_from_mouth_harp);
        mp.start();

        if (iY == floor) {
            change = jump;
        }
    }

    public void setWidth(int width)
    {
        iWidth = width;
    }

    public void setHeight(int height)
    {
        iHeight = height;
    }

    public float getWidth()
    {
        return iWidth;
    }

    public int getHeight()
    {
        return iHeight;
    }

    public void setX(int x)
    {
        iX = x;
    }

    public void setY(int y)
    {
        iY = y;
    }

    public int getX(){return iX;}

    public int getY(){return iY;}
}
