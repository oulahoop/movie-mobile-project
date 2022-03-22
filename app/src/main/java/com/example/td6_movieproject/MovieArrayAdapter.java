package com.example.td6_movieproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    public MovieArrayAdapter(@NonNull Context context, @NonNull List<Movie> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item_layout, parent, false);
        }

        TextView title = (TextView) convertView.findViewById(R.id.titleMovie);
        ImageView poster = (ImageView) convertView.findViewById(R.id.poster);

        title.setText(movie.getTitre());
        Picasso.get().load(API.IMAGE_URL.replaceAll("%path%", movie.getImage())).into(poster);

        return convertView;
    }
}
