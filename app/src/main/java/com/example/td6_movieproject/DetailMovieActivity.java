package com.example.td6_movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailMovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);


        ImageView poster = findViewById(R.id.posterDetails);
        TextView year = findViewById(R.id.yearDetails);
        TextView title = findViewById(R.id.titleDetails);
        TextView resume = findViewById(R.id.descriptionDetails);
        Button retour = findViewById(R.id.retour);

        Movie movie = getIntent().getParcelableExtra("movie");

        Picasso.get().load(API.IMAGE_URL.replaceAll("%path%", movie.getImage())).into(poster);
        year.setText(movie.getYear());
        title.setText(movie.getTitre());
        title.setMovementMethod(new ScrollingMovementMethod());
        resume.setMovementMethod(new ScrollingMovementMethod());
        resume.setText(movie.getResume());
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}