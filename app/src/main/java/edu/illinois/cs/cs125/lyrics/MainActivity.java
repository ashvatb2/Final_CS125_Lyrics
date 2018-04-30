package edu.illinois.cs.cs125.lyrics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

public final class MainActivity extends AppCompatActivity {
    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Lyrics: Main";

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    public String lyrics = "";
    /**
     * Run when our activity comes into view.
     *
     * @param savedInstanceState state that was saved by the activity last time it was paused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        // Load the main layout for our activity
        setContentView(R.layout.activity_main);

        final EditText song = findViewById(R.id.songName);
        final EditText artist = findViewById(R.id.artistName);

        // Attach the handler to our UI button
        final Button Find = findViewById(R.id.button);
        Find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Start API button clicked");
                startAPICall(song.getText().toString(), artist.getText().toString());
            }
        });

    }

    /**
     * Make an API call.
     */
    String result;
    void startAPICall(final String song, final String artist) {

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.lyrics.ovh/v1/"+artist+"/"+song,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            JsonParser parser = new JsonParser();
                            JsonObject result = parser.parse(response.toString()).getAsJsonObject();
                            if (result.has("lyrics")) {
                                lyrics = result.get("lyrics").getAsString();
                            } else {
                                lyrics = "Wrong input";
                            }
                            Intent intent = new Intent(MainActivity.this, DisplayLyrics.class);
                            intent.putExtra("LYRICS", lyrics);
                            startActivity(intent);
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                    Intent intent = new Intent(MainActivity.this, DisplayLyrics.class);
                    intent.putExtra("LYRICS", "Lyrics Not Found. Please Try Again");
                    startActivity(intent);
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
        }

    }
}