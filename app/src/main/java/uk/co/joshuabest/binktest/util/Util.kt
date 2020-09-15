package uk.co.joshuabest.binktest.util

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import uk.co.joshuabest.binktest.R

fun ImageView.loadImage(url: String?) {
    val progressDrawable = CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(this.context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun popToast(context: Context, text: String) =
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()