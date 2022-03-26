package com.example.td6_movieproject;

public class API {
    //KEY
    public static String APIKEY = "fad7d17da837a33b9fbb89d8201c4ed7";

    //USEFUL URL
    public static String URL_MOVIE_SEARCH = "https://api.themoviedb.org/3/search/movie?api_key=%apikey%&language=fr-FR&query=%query%";
    public static String URL_BEST_MOVIE = "https://api.themoviedb.org/3/discover/movie?api_key=%apikey%&language=fr-FR";
    public static String URL_GENRE = "https://api.themoviedb.org/3/genre/movie/list?api_key=%apikey%&language=fr-FR";
    public static String IMAGE_URL= "https://image.tmdb.org/t/p/w500/%path%";
}
