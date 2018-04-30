package edu.illinois.cs.cs125.lyrics;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DisplayLyrics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_lyrics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final TextView helloTextView = (TextView) findViewById(R.id.result);
        helloTextView.setText(getIntent().getStringExtra("LYRICS"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
