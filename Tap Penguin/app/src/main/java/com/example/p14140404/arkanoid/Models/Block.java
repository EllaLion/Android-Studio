package com.example.p14140404.arkanoid.Models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.widget.Toast;

import com.example.p14140404.arkanoid.R;

/**
 * Created by P14140404 on 25/04/2016.
 */
public class Block {
    private int iX;
    private int iY;
    private int iWidth;
    private int iHeight;
    private int iColour;


    private boolean gameOver = false;



    public Block()
    {

    }

    public Block(int x, int y, int width, int height, int colour)
    {
        iX = x;
        iY = y;
        iWidth = width;
        iHeight = height;
        iColour = colour;
    }

    public void drawRect(Paint p, Canvas c)
    {
        int top = iY;
        int left = iX;
        int bottom = iY + iHeight;
        float right = iX + iWidth;

        //Rect rectangle = new Rect(left, top, right, bottom);

        p.setColor(iColour);

        c.drawRect(left, top, right, bottom, p);
    }

    public int update(Point screenSize, Penguin pen, Context context, int iSpeed)
    {

        iX-=iSpeed;

        if (iX < -20)
        {
            iX = screenSize.x; // Resets position
            if (!gameOver) {
                return 0; // Score a point
            }
        }

        if (iX < pen.getX()+pen.getWidth() && iX+iWidth > pen.getX())
        {
            if (iY > pen.getY() && iY+iHeight < pen.getY()+pen.getHeight())
            {
                MediaPlayer mp = MediaPlayer.create(context, R.raw.low_tone_boing);
                mp.start();

                gameOver = true;

                if (gameOver){
                    mp.stop();
                }
                return 1; // game over
            }
        }

        return 2; // nothing happens
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

    public void setColour(int r, int g, int b)
    {
        iColour = Color.rgb(r, g, b);
    }
}