package com.example.filmes.data.database.converters

import androidx.room.TypeConverter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT)//"2022-04-12 12:11:46"

    @TypeConverter
    fun fromString(value: String?): Date? {
        if (value != null) {
            try {
                return formatter.parse(value)
            }
            catch (e: ParseException) {
                e.printStackTrace()
            }
            return null
        }
        else
            return null
    }

    @TypeConverter
    fun dateToString(value: Date?): String?  {
        return if (value != null)
            formatter.format(value)
        else
            null
    }
}