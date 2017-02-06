package com.example.p14140404.arkanoid.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.p14140404.arkanoid.Models.Block;
import com.example.p14140404.arkanoid.Models.Penguin;
import com.example.p14140404.arkanoid.Models.Score;
import com.example.p14140404.arkanoid.R;

import java.util.ArrayList;

/**
 * Created by P14140404 on 25/04/2016.
 */
public class GameSurfaceView extends SurfaceView implements Runnable  {

    Point screenSize;
    SurfaceHolder holder;
    private boolean ok = false;
    Thread t = null;
    Context context;
    Paint paint = new Paint();
    private final static int    MAX_FPS = 50;                   // desired fps
    private final static int    MAX_FRAME_SKIPS = 5;            // maximum number of frames to be skipped
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;  // the frame period
    private long beginTime;                                     // the time when the cycle began
    private long timeDiff;                                      // the time it took for the cycle to execute
    private int sleepTime;                                      // ms to sleep
    private int framesSkipped;                                  // number of frames being skipped
    private int iSpeed = 5;

    boolean gameOver = false;
    Block[] blocks = new Block[4];
    Penguin pen;
    Score score;


    /*ArrayList<BreakBlock> bbArray = new ArrayList<>();*/

    public GameSurfaceView(Context context, Point screenS)
    {
        super(context);
        holder = getHolder();
        this.context = context;
        screenSize = screenS;
        score = new Score(screenSize, context);
        blocks[0] = new Block(screenSize.x+screenSize.x/4,screenSize.y/6*4,20,-70,Color.BLUE);
        blocks[1] = new Block(screenSize.x+screenSize.x/4*2,screenSize.y/6*4,20,-100,Color.YELLOW);
        blocks[2] = new Block(screenSize.x+screenSize.x/4*3,screenSize.y/6*4,20,-70,Color.GREEN);
        blocks[3] = new Block(screenSize.x+screenSize.x,screenSize.y/6*4,20,-100,Color.BLACK);
        blockConstruct();

        pen = new Penguin(50,screenSize.y/6*4-60,50,50,context);

       /* MediaPlayer mp = MediaPlayer.create(context, R.raw.sweetgum_tree);
        mp.start();

        if (gameOver){
            mp.stop();
        }*/
    }

    public void tap(float xPos, float yPos)
    {
        if (gameOver)
        {
            ((Activity) getContext()).onBackPressed();
        }
        else {
            pen.jump(context);
        }
    }

    private void updateCanvas()
    {
        //drawCanvas(this, paint);
        for (int i=0; i<4; i++) {
            int j = blocks[i].update(screenSize, pen, context, iSpeed);

            if (j == 0)
            {
                iSpeed++;
                score.scored();
            }
            else if (j == 1)
            {
                //game over
                score.gameOver();
                gameOver = true;
            }
        }

        pen.update();
        if (gameOver){
            iSpeed = 0;
        }
    }

    private void blockConstruct() {
        //Used to construct the blocks
        //Here is where you add your block construction using rectangle
        int bufferX = 20;
        int bufferY = 50;

        int blockAmount = 10;
        //int width = t;
        float blockWidth =  (screenSize.x / blockAmount); //width of one block if we use more than the screen
        float width = blockWidth - bufferX;

        //Log.d("width",String.valueOf(width));
        //Log.d("blockWidth", String.valueOf(blockWidth));

        //Log.d("width", width);

        //int width = screenSize.x / blockWidth;

        int height = 30;
        int startX = 0;
        int startY = 50;

        int currentX = startX;
        int currentY = startY;

        for(int y=0; y < 3; y++)
        {
           /* for(int x = 0; x < blockAmount; x++)
            {
                bbArray.add(new BreakBlock(currentX + bufferX, currentY, width, height, Color.BLACK));
                currentX += bufferX + width;
            }
            */
            currentX = startX;
            currentY += bufferY;
        }

    }


    private void drawCanvas(Canvas canvas, Paint paint)
    {
        canvas.drawARGB(255, 230, 254, 254);
        for (int i=0; i<4; i++) {
            blocks[i].drawRect(paint, canvas);
        }
        pen.draw(canvas);
        score.draw(paint, canvas);

        //update();

        /*for(int i=0; i < bbArray.size(); i++)
        {
            bbArray.get(i).drawRect(paint, canvas);
        }*/

        //hHandler.postDelayed(r, 10);
    }

    public void run() {
        //Remove conflict between the UI thread and the game thread.
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND);

        while (ok) {
            //perform canvas drawing
            if (!holder.getSurface().isValid()) {//if surface is not valid
                continue;//skip anything below it
            }
            Canvas c = holder.lockCanvas(); //Lock canvas, paint canvas, unlock canvas
            synchronized (holder) {
                beginTime = System.currentTimeMillis();
                framesSkipped = 0;  // resetting the frames skipped
                // update game state
                this.updateCanvas();
                // render state to the screen
                // draws the canvas on the panel
                this.drawCanvas(c, paint);
                // calculate how long did the cycle take
                timeDiff = System.currentTimeMillis() - beginTime;
                // calculate sleep time
                sleepTime = (int) (FRAME_PERIOD - timeDiff);
                if (sleepTime > 0) {
                    // if sleepTime > 0 put to sleep for short period of time
                    try {
                        // send the thread to sleep for a short period
                        // very useful for battery saving
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }

                Log.d("Sleep Time", String.valueOf(sleepTime));
                //ADD THIS IF WE ARE DOING LOTS OF WORK
                //If sleeptime is greater than a frame length, skip a number of frames
                while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                    // we need to catch up
                    // update without rendering
                    this.updateCanvas();
                    // add frame period to check if in next frame
                    sleepTime += FRAME_PERIOD;
                    framesSkipped++;
                    Log.d("Skipping","Skip");
                }

                holder.unlockCanvasAndPost(c);
            }
        }
    }

    public void pause()
    {
        ok = false;
        while(true)
        {
            try {
                t.join();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }

    public void resume()
    {
        ok = true;
        t = new Thread(this);
        t.start();
    }
}
