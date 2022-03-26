package com.example.td6_movieproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchFilmActivitiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_film_activitiy);

        EditText inputResearch = findViewById(R.id.researchMovie);
        EditText yearResearch = findViewById(R.id.date);
        Spinner genre = findViewById(R.id.genreSpinner);
        Button research = findViewById(R.id.research);
        Button retour = findViewById(R.id.retourAccueil2);

        HashMap<String,Integer> map = new HashMap<>();
        map.put("Tous",0);
        Ion.with(this)
                .load(API.URL_GENRE.replaceAll("%apikey%", API.APIKEY))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e!=null){
                            return;
                        }
                        result.getAsJsonArray("genres").forEach(jsonElement -> {
                            JsonObject json = jsonElement.getAsJsonObject();
                            map.put(json.get("name").getAsString(), json.get("id").getAsInt());
                        });
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchFilmActivitiy.this, R.layout.spinner_item_layout, new ArrayList<>(map.keySet()));
                        genre.setAdapter(adapter);
                    }
                });
        research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.getDefault(v.getContext()).getConscryptMiddleware().enable(false);


                if (inputResearch.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), "Aucun titre n'a été précisé", Toast.LENGTH_SHORT).show();
                    return;
                }
                String year = "";
                try {
                    year += "&year=" + Integer.valueOf(yearResearch.getText().toString());
                } catch (Exception ignored) {
                }
                String genreResearch = "";
                if(!genre.getSelectedItem().toString().equalsIgnoreCase("Tous")) {
                    genreResearch = "&with_genres=" + map.get(genre.getSelectedItem().toString());
                }
                String query = inputResearch.getText().toString();
                String url = API.URL_MOVIE_SEARCH.replaceAll("%apikey%", API.APIKEY).replaceAll("%query%", query) + year + genreResearch;
                System.out.println(url);
                Intent intentListMovie = new Intent(SearchFilmActivitiy.this, ListMovieActivity.class);
                intentListMovie.putExtra("url", url);
                startActivity(intentListMovie);
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}