package com.example.filmes.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.filmes.domain.model.MovieDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SharedPreferecesConfig(val sharedPreferences: SharedPreferences) {

    fun setListaSalva(listMovie : ArrayList<MovieDto>){
        sharedPreferences.edit {
            val json = Gson().toJson(listMovie)
            putString("listFilmesFavoritos", json).apply()
        }
    }

    fun getListaSalva() : ArrayList<MovieDto>{
        var movieList = sharedPreferences.getString("listFilmesFavoritos", "[]")
        val turnsType = object : TypeToken<List<MovieDto>>() {}.type

        return Gson().fromJson(movieList, turnsType)
    }
}