package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.rantmedia.joketeller.TellAJoke;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeResponse {

    private InterstitialAd mInterstitialAd;
    private String mLoadedJoke;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        //request interstitial ad
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                requestJokeFromServer();
            }
        });

        requestNewInterstitial();

        final Button tellJoke = (Button)root.findViewById(R.id.tellJokeButton);

        tellJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke(v);
            }
        });

        return root;
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
        fetchJokeTask.execute(getContext());
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
            Toast toast = Toast.makeText(getContext(), text, duration);
            toast.show();
        }
    }

    //launch activity
    private void launchJokeActivity() {
        if(mLoadedJoke != null) {
            Intent sendIntent = new Intent(getContext(), TellAJoke.class);
            sendIntent.putExtra("joke", mLoadedJoke);
            startActivity(sendIntent);
        }
    }
}
