package com.example.movies.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {

        fun parseDate(date: String?): Date? {
            return try {
                val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT)
                if (date == "null") null else if(date == null) null else formatter.parse(date)
            } catch (e: Exception){
                null
            }
        }

        fun showDate(date: Date?): String? {
            return try {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
                if(date == null) null else formatter.format(date)
            } catch (e: Exception){
                null
            }
        }

        fun fromIntArray(json: String?): List<Int> {
            val turnsType = object : TypeToken<List<Int>>() {}.type
            return Gson().fromJson(json, turnsType)
        }

        fun fromJson(listaGeneros: List<Int>): String = Gson().toJson(listaGeneros)
    }
}