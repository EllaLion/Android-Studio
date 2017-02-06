package com.example.p14140404.arkanoid.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.p14140404.arkanoid.Controller.GameActivity;
import com.example.p14140404.arkanoid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Message", "Hello world!");
        Toast.makeText(this, "OnCreate", Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_main);

    }

    protected void onButtonClicked()
    {
        Log.v("Message", "The button was clicked yay!");
    }

    protected void onPause(Bundle savedInstanceState){
        super.onPause();
        Toast.makeText(this, "OnPause", Toast.LENGTH_LONG).show();
    }

    protected void onResume(Bundle savedInstanceState){
        super.onResume();
        Toast.makeText(this, "OnResume", Toast.LENGTH_LONG).show();
    }

    public void startGame(View view){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
