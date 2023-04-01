package com.dicoding.core.utils

import android.annotation.SuppressLint
import com.dicoding.core.domain.model.game.GenreModel
import java.text.SimpleDateFormat
import java.util.Calendar

object DataHelper {

    const val EXTRA_DATA = "EXTRA_DATA"
    private const val DATE_PATTERN = "dd MMMM yyyy"

    @SuppressLint("SimpleDateFormat")
    fun formatDate(date: String): String {
        var dateTime = "-"
        try {
            val dates = date.split("-")
            val simpleDateFormat = SimpleDateFormat(DATE_PATTERN)
            val calendar = Calendar.getInstance()
            calendar.set(dates[0].toInt(), dates[1].toInt() - 1, dates[2].toInt())
            dateTime = simpleDateFormat.format(calendar.time).toString()
        } catch (e: Exception){
            e.printStackTrace()
        }
        return dateTime
    }

    fun genreListing(list: List<GenreModel>): String {
        var genreShow = ""
        list.map {
            genreShow += it.name+", "
        }
        if (genreShow.isNotEmpty()){
            return genreShow.dropLast(2)
        }
        return "-"
    }

    fun gamesCount(count: Int): String {
        val result = buildString {
            append(count)
            append(" games")
        }
        return result
    }

}