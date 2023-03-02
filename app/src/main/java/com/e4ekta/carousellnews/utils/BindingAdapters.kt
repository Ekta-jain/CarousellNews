package com.e4ekta.carousellnews.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.e4ekta.carousellnews.R
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.imageFromUrl(url: String?) {
        if (!url.isNullOrEmpty()) {
            Glide.with(this.context).load(url).into(this)
        }
    }

    @BindingAdapter("setDuration")
    @JvmStatic
    fun AppCompatTextView.setDuration(timeCreated: Long?) {
        val timeCreatedms = timeCreated!!.toLong() * 1000
        this.text = this.context.getString(
            R.string.duration,
            PrettyTime().format(Date(timeCreatedms)),
            SimpleDateFormat(Constants.pattern, Locale.getDefault()).format(Date(timeCreatedms))
        )
    }
}
