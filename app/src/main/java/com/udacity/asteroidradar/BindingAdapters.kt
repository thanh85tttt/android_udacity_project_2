package com.udacity.asteroidradar

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
  val resId = if (isHazardous) R.drawable.ic_status_potentially_hazardous
  else R.drawable.ic_status_normal
  imageView.setImageResource(resId)
}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
  val resId = if (isHazardous) R.drawable.asteroid_hazardous
  else R.drawable.asteroid_safe
  val contentDesc = if (isHazardous) R.string.potentially_hazardous_asteroid_image
  else R.string.not_hazardous_asteroid_image
  with(imageView) {
    setImageResource(resId)
    contentDescription = context.getString(contentDesc)
  }
}

@BindingAdapter("astronomicalUnitText")
fun bindAstronomicalUnitText(textView: TextView, number: Double) {
  bindTextViewToUnit(textView, R.string.astronomical_unit_format, number)
}

@BindingAdapter("kmUnitText")
fun bindKmUnitText(textView: TextView, number: Double) {
  bindTextViewToUnit(textView, R.string.km_unit_format, number)
}

@BindingAdapter("velocityText")
fun bindVelocityText(textView: TextView, number: Double) {
  bindTextViewToUnit(textView, R.string.km_s_unit_format, number)
}

private fun bindTextViewToUnit(textView: TextView, formatResId: Int, number: Double) {
  val context = textView.context
  textView.text = String.format(context.getString(formatResId), number)
}

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
  imgUrl?.let {
    val imgUri = it.toUri().buildUpon().scheme("https").build()
    Glide.with(imgView).load(imgUri).into(imgView)
  }
}

@BindingAdapter("contentDescriptionBind")
fun bindImageContentDescription(imgView: ImageView, titleImage: String?) {
  val context = imgView.context
  imgView.contentDescription = if (titleImage.isNullOrEmpty()) {
    context.getString(R.string.this_is_nasa_s_picture_of_day_showing_nothing_yet)
  } else {
    context.getString(R.string.nasa_picture_of_day_content_description_format, titleImage)
  }
}

