package com.example.jokepresenterlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {
    public static final String ARG_JOKE = "jokes";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        String jokeString = getIntent().getStringExtra(ARG_JOKE);
        TextView jokeTV = (TextView) findViewById(R.id.jokes);
        jokeTV.setText(jokeString);

    }
}
