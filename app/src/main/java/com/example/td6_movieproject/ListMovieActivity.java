package com.example.td6_movieproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
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
                        ListView lv = findViewById(R.id.listMovie);
                        lv.setAdapter(adapter);
                    }
                });
        /*

        */

    }
}