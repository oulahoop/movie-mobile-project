package com.example.td6_movieproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class ListMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie);
        String url = getIntent().getStringExtra("url");

        ListView listMovie = findViewById(R.id.listMovie);
        Button retour = findViewById(R.id.retourButton);


        Ion.with(ListMovieActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onCompleted(Exception e, JsonObject res) {
                        if (e != null && res == null) {
                            System.out.println(e.getMessage());
                            return;
                        }
                        ArrayList<Movie> movieArrayList = new ArrayList<>();
                        JsonArray result = res.getAsJsonArray("results");
                        //int nombreDeRecherche = 50;
                        for(JsonElement element : result){
                            JsonObject json = element.getAsJsonObject();
                            movieArrayList.add(new Movie(json));
                        }

                        MovieArrayAdapter adapter = new MovieArrayAdapter(ListMovieActivity.this, movieArrayList);
                        listMovie.setAdapter(adapter);
                    }
                });

        listMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailIntent = new Intent(ListMovieActivity.this, DetailMovieActivity.class);
                Movie m = (Movie) parent.getAdapter().getItem(position);
                detailIntent.putExtra("movie",m);
                startActivity(detailIntent);
            }
        });
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*

        */

    }
}