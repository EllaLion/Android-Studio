package com.example.p14140404.arkanoid.Controller;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.p14140404.arkanoid.R;
import com.example.p14140404.arkanoid.View.GameSurfaceView;

public class GameActivity extends AppCompatActivity {

    //private GameView gv;
    private GameSurfaceView gsv;
    private Point screenSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int heightPixels = metrics.heightPixels;
        int widthPixels = metrics.widthPixels;
        screenSize = new Point(widthPixels, heightPixels);

        /*gv = new GameView(this, screenSize);
        setContentView(gv);*/

        gsv = new GameSurfaceView(this, screenSize);

        setContentView(gsv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        gsv.resume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        gsv.pause();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int eventaction = event.getAction();

        float xPos, yPos;
        switch (eventaction) {
            case MotionEvent.ACTION_DOWN: // finger touches the screen
                xPos = event.getX();
                yPos = event.getY();
                gsv.tap(xPos, yPos);
                break;
            case MotionEvent.ACTION_MOVE: // finger moves on the screen
                xPos = event.getX();
                yPos = event.getY();
                break;
            case MotionEvent.ACTION_UP: // finger leaves the screen
                break;
        }
        // tell the system that we handled the event and no further
        return true;
    }
}