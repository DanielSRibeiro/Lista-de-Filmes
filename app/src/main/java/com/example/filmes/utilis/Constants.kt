package com.example.filmes.utilis

import android.content.Context
import android.widget.Toast

val BASE_IMAGEM = "https://image.tmdb.org/t/p/original/"
val BASE_URL = "https://api.themoviedb.org/3/"
val USER_KEY = "ae69ec81dd60f4108a88423265126573"
val LANGUAGE = "pt-br"
val TAG_MOVIE = "movie-popular"
val TAG_CATEGORIES = "all-categories"

val TAG_INSERT = "all-categories"
val TAG_SELECT = "select_movie"
val TAG_VERIFY = "verify_movie"

fun Context.showToast(toast : String){
    Toast.makeText(this, toast, Toast.LENGTH_LONG).show()
}