package com.example.filmes.utilis

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class JsonService {
    companion object{
        fun fromIntArray(json: String?): IntArray {
            val turnsType = object : TypeToken<IntArray>() {}.type
            return Gson().fromJson(json, turnsType)
        }

        fun fromJson(listaGeneros: IntArray): String = Gson().toJson(listaGeneros)
    }
}