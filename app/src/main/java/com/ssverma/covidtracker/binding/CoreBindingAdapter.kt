package com.ssverma.covidtracker.binding

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.ssverma.covidtracker.R

@BindingAdapter(
    value = [
        "bind:imageUrl",
        "bind:isCircular",
        "bind:localSrc",
        "bind:placeholder",
        "bind:ignorePlaceholder",
        "bind:imageRequestListener"
    ],
    requireAll = false
)
fun loadImage(
    imageView: ImageView,
    imageUrl: String?,
    isCircular: Boolean = false,
    @DrawableRes localSrc: Int = 0,
    @DrawableRes placeholder: Int = 0,
    ignorePlaceholder: Boolean = false,
    imageListener: RequestListener<Drawable>? = null
) {
    Glide.with(imageView).load(if (localSrc == 0) imageUrl else localSrc).apply {
        if (isCircular) {
            apply(RequestOptions.circleCropTransform())
        }

        imageListener?.let {
            listener(it)
        }

        val drawable = if (placeholder != 0) {
            placeholder
        } else if (isCircular) {
            R.drawable.image_placeholder_circle
        } else {
            R.drawable.image_placeholder
        }

        if (!ignorePlaceholder) {
            apply(RequestOptions.placeholderOf(drawable))
                .apply(RequestOptions.errorOf(drawable))
        }

    }.into(imageView)
}

@BindingAdapter("shouldShow")
fun toggleVisibility(view: View, shouldShow: Boolean) {
    view.visibility = if (shouldShow) View.VISIBLE else View.GONE
}

@BindingAdapter(
    value = [
        "bind:isOnState",
        "bind:onStateDrawable",
        "bind:offStateDrawable"
    ], requireAll = true
)
fun toggleDrawable(
    imageView: ImageView,
    isOnState: Boolean, onStateDrawable: Drawable, offStateDrawable: Drawable
) {
    imageView.setImageDrawable(if (isOnState) onStateDrawable else offStateDrawable)
}