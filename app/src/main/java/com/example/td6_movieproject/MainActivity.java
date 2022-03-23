package com.example.td6_movieproject;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputResearch = findViewById(R.id.researchMovie);
        EditText numberResearch = findViewById(R.id.numberResearch);
        EditText yearResearch = findViewById(R.id.date);
        Spinner genre = findViewById(R.id.genreSpinner);
        Button research = findViewById(R.id.research);



        research.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ion.getDefault(v.getContext()).getConscryptMiddleware().enable(false);


                if(inputResearch.getText().toString().isEmpty()){
                    Toast.makeText(v.getContext(), "Aucun titre n'a été précisé",Toast.LENGTH_SHORT).show();
                    return;
                }


                try{
                    int year = Integer.parseInt(yearResearch.getText().toString());
                    String query = inputResearch.getText().toString();
                    System.out.println("OK1");
                    Ion.with(v.getContext())
                            .load(API.URL.replaceAll("%apikey%",API.APIKEY).replaceAll("%query%", query) + "&year="+year)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @RequiresApi(api = Build.VERSION_CODES.N)
                                @Override
                                public void onCompleted(Exception e, JsonObject res) {
                                    System.out.println("OK2");
                                    if (e != null && res == null) {
                                        System.out.println(e.getMessage());
                                        return;
                                    }
                                    JsonArray result = res.getAsJsonArray("results");
                                    System.out.println("OK3");
                                    int nombreDeRecherche;
                                    try{
                                        nombreDeRecherche = Integer.parseInt(numberResearch.getText().toString());
                                        nombreDeRecherche = nombreDeRecherche>50 ? 50 : nombreDeRecherche<=0 ? 50 : nombreDeRecherche;
                                    }catch (Exception ignored){
                                        nombreDeRecherche = 50;
                                    }
                                    ArrayList<Movie> movieArrayList = new ArrayList<>(nombreDeRecherche);
                                    int i = 0;
                                    for(JsonElement element : result){
                                        JsonObject json = element.getAsJsonObject();
                                        movieArrayList.add(new Movie(json));
                                        i++;
                                        if(i>nombreDeRecherche){
                                            break;
                                        }
                                    }
                                    System.out.println("OKOKOKOKOKOKOKOKOOKKOKOKOKOKOKOKO");
                                    MovieArrayAdapter adapter = new MovieArrayAdapter(MainActivity.this, movieArrayList);
                                    ListView lv = findViewById(R.id.listMovie);
                                    lv.setAdapter(adapter);
                                    startActivity(new Intent(MainActivity.this, ListMovieActivity.class));
                                }
                            });

                }catch (NumberFormatException e){
                    String query = inputResearch.getText().toString();
                    System.out.println("onClick: " + query);
                    Ion.with(v.getContext())
                            .load(API.URL.replaceAll("%apikey%",API.APIKEY).replaceAll("%query%", query)).asJsonArray();
                }
            }
        });
    }
}