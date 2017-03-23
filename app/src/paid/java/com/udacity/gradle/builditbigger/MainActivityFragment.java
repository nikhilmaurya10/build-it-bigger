package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.jokepresenterlib.JokeActivity;

/**
 * Created by Nikhi on 24-03-2017.
 */

public class MainActivityFragment extends Fragment implements EndpointsAsyncTask.Callback{
    ProgressBar pb;
    Button button;

    public MainActivityFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        pb = (ProgressBar) root.findViewById(R.id.progressBar);
        button = (Button) root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                tellJoke();
            }
        });
        return root;
    }

    void showLoading() {
        pb.setVisibility(View.VISIBLE);
        button.setEnabled(false);
    }
    void hideLoading() {
        pb.setVisibility(View.INVISIBLE);
    }
    public void tellJoke() {
        new EndpointsAsyncTask(this).execute(getActivity());
    }

    @Override
    public void done(String result) {
        hideLoading();
        if (result!= null || result !=""){
            button.setEnabled(true);
            Intent jokeIntent = new Intent(getActivity(), JokeActivity.class);
            jokeIntent.putExtra(JokeActivity.ARG_JOKE, result);
            startActivity(jokeIntent);
        }
    }
}
