package com.rantmedia.joketeller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TellAJoke extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_ajoke);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView jokeView = (TextView) findViewById(R.id.jokeView);

        //get joke from intent extra
        Intent intent = getIntent();
        String joke = intent.getStringExtra("joke");

        if(joke != null){
            jokeView.setText(joke);
        }


    }

}
