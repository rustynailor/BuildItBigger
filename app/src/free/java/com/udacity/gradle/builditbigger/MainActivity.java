package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.rantmedia.joketeller.TellAJoke;


public class MainActivity extends AppCompatActivity implements JokeResponse{

    private InterstitialAd mInterstitialAd;
    private String mLoadedJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                requestJokeFromServer();
            }
        });

        requestNewInterstitial();
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

    //request interstitial ad
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    //show interstitial ad on click
    public void tellJoke(View view){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            requestJokeFromServer();
        }
    }

    //request a joke
    private void requestJokeFromServer() {
        FetchJokeTask fetchJokeTask = new FetchJokeTask();
        fetchJokeTask.delegate = this;
        //execute the async task
        fetchJokeTask.execute(this);
    }

    //called by asyntask in onPostExecute
    //either load joke into member var or show error
    @Override
    public void processFinish(String output) {
        //launch activity if we have a joke
        if(output != null) {
            mLoadedJoke = output;
            launchJokeActivity();
        }   //if not, show error
        else {
            CharSequence text = getString(R.string.joke_error);
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        }
    }

    //launch activity
    private void launchJokeActivity() {
        if(mLoadedJoke != null) {
            Intent sendIntent = new Intent(getApplicationContext(), TellAJoke.class);
            sendIntent.putExtra("joke", mLoadedJoke);
            startActivity(sendIntent);
        }
    }
}
