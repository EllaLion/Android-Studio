package com.example.p14140404.arkanoid.Models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * Created by P14140404 on 25/04/2016.
 */
public class Score {
    Point screenSize;
    Context context;
    int score = 0;
    boolean gameOver = false;

    public Score(Point screenSize, Context context)
    {
        this.screenSize = screenSize;
        this.context = context;
    }

    public void scored()
    {
        score++;
    }

    public void draw(Paint p, Canvas c)
    {
       /* if (gameOver)
        {
            p.setColor(Color.LTGRAY);
            c.drawRect(60, 30, screenSize.x - 170, screenSize.y - 170, p);
        }*/

        p.setColor(Color.BLACK);
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(30);
        c.drawText("Score: " + score, screenSize.x/2, screenSize.y/2, p);

        if (gameOver)
        {
            c.drawText("GAME OVER", screenSize.x/2, screenSize.y/3, p);

            p.setColor(Color.BLACK);
            c.drawText("Tap to return", screenSize.x/2, screenSize.y/3*2, p);
        }
    }

    public void gameOver()
    {
        gameOver = true;
    }
}
