package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jokepresenterlib.JokeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.Callback {
    String jokeString;
    boolean ready=false;
    Button button;
    ProgressBar progressBar;
    InterstitialAd mInterstitialAd;
    EndpointsAsyncTask.Callback cb;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        button = (Button) root.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                tellJoke();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    progressBar.setVisibility(View.VISIBLE); //using progressbar visibility as a clue that ad is not loaded
                }

            }
        });
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                progressBar.setVisibility(View.VISIBLE);
                if(ready) {
                    launchActivity();
                }
            }
        });

        requestNewInterstitial();


        return root;
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
    void tellJoke() {
        ready = false;
        new EndpointsAsyncTask(this).execute(getActivity());
    }

    @Override
    public void done(String result) {
        jokeString = result;
        ready = true;
        if (progressBar.getVisibility() == View.VISIBLE) { //launch as soon as possible in case of ad is not loaded
            launchActivity();
        }
    }
    void launchActivity(){
        progressBar.setVisibility(View.INVISIBLE);
        button.setEnabled(true);
        Intent jokeIntent = new Intent(getActivity(), JokeActivity.class);
        jokeIntent.putExtra(JokeActivity.ARG_JOKE, jokeString);
        startActivity(jokeIntent);
    }
}
