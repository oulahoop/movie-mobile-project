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
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BestFilmsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best_films);
        Button recherche = findViewById(R.id.recherche);
        Button retour = findViewById(R.id.retourAccueil);
        RadioGroup mode = findViewById(R.id.group_mode);
        EditText annee = findViewById(R.id.year);
        Spinner theme = findViewById(R.id.theme);

        mode.check(R.id.popularity);//DEFAULT MODE R.id.vote
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
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(BestFilmsActivity.this, R.layout.spinner_item_layout, new ArrayList<>(map.keySet()));
                        theme.setAdapter(adapter);
                    }
                });
        recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yearResearch = "";
                try{
                    yearResearch+= "&primary_release_year=" + Integer.valueOf(annee.getText().toString()).toString();
                }catch (Exception ignored){}
                String themeReseach = "";
                if(!theme.getSelectedItem().toString().equalsIgnoreCase("Tous")){
                    themeReseach = "&with_genres=" + map.get(theme.getSelectedItem().toString());
                }
                String modeResearch = "&sort_by=" + (mode.getCheckedRadioButtonId()==R.id.popularity ? "popularity.desc" : "vote_average.desc");
                Intent intent = new Intent(BestFilmsActivity.this, ListMovieActivity.class);
                intent.putExtra("url",API.URL_BEST_MOVIE.replaceAll("%apikey%",API.APIKEY) + yearResearch + themeReseach + modeResearch);
                startActivity(intent);
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