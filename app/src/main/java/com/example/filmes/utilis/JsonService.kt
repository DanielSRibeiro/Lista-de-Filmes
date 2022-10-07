package com.example.filmes.utilis

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonService {
    companion object{
        fun fromIntArray(json: String?): List<Int> {
            val turnsType = object : TypeToken<List<Int>>() {}.type
            return Gson().fromJson(json, turnsType)
        }

        fun fromJson(listaGeneros: List<Int>): String = Gson().toJson(listaGeneros)
    }
}