package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rantmedia.joketeller.TellAJoke;
import com.udacity.gradle.builditbigger.FetchJokeTask;
import com.udacity.gradle.builditbigger.JokeResponse;
import com.udacity.gradle.builditbigger.R;


public class MainActivity extends ActionBarActivity implements JokeResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void tellJoke(View view){
        FetchJokeTask fetchJokeTask =new FetchJokeTask();
        fetchJokeTask.delegate = this;
        //execute the async task
        fetchJokeTask.execute(this);
    }

    //called by asyntask in onPostExecute
    @Override
    public void processFinish(String output) {
        //launch activity
        if(output != null) {
            Intent sendIntent = new Intent(getApplicationContext(), TellAJoke.class);
            sendIntent.putExtra("joke", output);
            startActivity(sendIntent);
        }
    }
}
