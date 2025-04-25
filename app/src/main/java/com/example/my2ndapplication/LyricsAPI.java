package com.example.my2ndapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LyricsAPI extends AppCompatActivity {
    private SearchView searchView;
    private TextView lyricsTextView, titleTextView;
    private ProgressBar progressBar;
    private LyricsView viewModel;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics_api);
        setupEdgeToEdge();
        initializeViews();
        setupViewModel();
        setupSearchView();

        requestQueue = Volley.newRequestQueue(this);
    }

    private void setupEdgeToEdge() {
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initializeViews() {
        searchView = findViewById(R.id.search_bar);
        lyricsTextView = findViewById(R.id.lyricsbar);
        titleTextView = findViewById(R.id.barlabel);
        progressBar = findViewById(R.id.progressBar);


        Intent intent = getIntent();
        String title = intent.getStringExtra("name3");
        if (title != null && !title.isEmpty()) {
            titleTextView.setText(title);
        }
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(LyricsView.class);

        viewModel.getLyrics().observe(this, lyrics -> {
            lyricsTextView.setText(lyrics);
            progressBar.setVisibility(View.GONE);
        });

        viewModel.getError().observe(this, error -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        });
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.trim().isEmpty()) {
                    Toast.makeText(LyricsAPI.this, "Please enter a song title", Toast.LENGTH_SHORT).show();
                    return false;
                }
                fetchLyrics(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void fetchLyrics(String query) {
        progressBar.setVisibility(View.VISIBLE);
        lyricsTextView.setText("");


        String[] parts = query.split("\\s*-\\s*|\\s*by\\s*|\\s*:\\s*", 2);
        String artist = parts.length > 1 ? parts[0].trim() : "";
        String title = parts.length > 1 ? parts[1].trim() : query.trim();

        String url = "https://api.lyrics.ovh/v1/"
                + Uri.encode(artist) + "/"
                + Uri.encode(title);

        Log.d("LyricsSearch", "Requesting URL: " + url);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String lyrics = jsonObject.optString("lyrics", "");
                        if (!lyrics.isEmpty()) {
                            viewModel.setLyrics(formatLyrics(lyrics));
                        } else {
                            viewModel.setError("Lyrics not found for this song");
                        }
                    } catch (JSONException e) {
                        viewModel.setError("Error parsing lyrics data");
                    }
                },
                error -> {
                    String errorMsg;
                    if (error instanceof TimeoutError) {
                        errorMsg = "Request timed out. Please try again.";
                    } else if (error instanceof NetworkError) {
                        errorMsg = "Network error. Please check your connection.";
                    } else {
                        errorMsg = "Failed to fetch lyrics. Please try again later.";
                    }
                    viewModel.setError(errorMsg);
                });

        requestQueue.add(request);
    }

    private String formatLyrics(String lyrics) {
        // Basic formatting - you can enhance this further
        return lyrics.replaceAll("\n", "\n\n");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (requestQueue != null) {
            requestQueue.cancelAll(tag -> true);
        }
    }
}