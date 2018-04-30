package com.example.m_learning4socialskills;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;



public class MainActivity extends Activity 
{

    private static  int SPLASH_TIME_OUT=4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {

                Intent occupationIntent = new Intent(MainActivity.this,Occupation.class);
                startActivity(occupationIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
