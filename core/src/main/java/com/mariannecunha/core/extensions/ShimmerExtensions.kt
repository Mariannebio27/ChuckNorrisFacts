package com.mariannecunha.core.extensions

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout?.show() {
    this?.visibility = View.VISIBLE
    this?.startShimmer()
}

fun ShimmerFrameLayout?.hide() {
    this?.visibility = View.GONE
    this?.stopShimmer()
}
