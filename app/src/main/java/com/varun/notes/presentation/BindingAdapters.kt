package com.varun.notes.presentation

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("date")
    fun bindDate(textView: TextView, timeStamp: Long) {
        val parse = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
        val date = Date(timeStamp)
        textView.text = parse.format(date).toString()
    }
}
