package com.mariannecunha.core.extensions

import android.view.View
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.google.android.material.snackbar.Snackbar
import com.mariannecunha.core.R
import com.mariannecunha.core.extensions.model.SnackbarProperties

fun View.showSnackbar(properties: SnackbarProperties = SnackbarProperties()) {
    return Snackbar
        .make(
            this,
            this.context.getText(properties.textResId),
            properties.duration
        )
        .setTextGravity()
        .setIcon(properties.iconResId)
        .applyBackground(properties.backgroundResId)
        .show()
}

private fun Snackbar.setTextGravity(): Snackbar {
    val textView = getTextView()
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    return this
}

private fun Snackbar.setIcon(@DrawableRes iconResId: Int): Snackbar {
    val textView = getTextView()
    textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, iconResId, 0)
    return this
}

private fun Snackbar.applyBackground(backgroundResId: Int): Snackbar {
    val backgroundColor = this.context.getColor(backgroundResId)
    setBackgroundTint(backgroundColor)
    return this
}

private fun Snackbar.getTextView() = this.view.findViewById(R.id.snackbar_text) as TextView
