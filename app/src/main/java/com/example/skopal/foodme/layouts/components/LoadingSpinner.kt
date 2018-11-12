package com.example.skopal.foodme.layouts.components

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.example.skopal.foodme.R

/**
 * A [RelativeLayout] that can be adjusted to a specified aspect ratio.
 */
class LoadingSpinner : RelativeLayout {
    @JvmOverloads
    constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyle: Int = 0
    ) : super(context, attrs, defStyle)

    /**
     * Support for KitKat and earlier
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        LayoutInflater.from(context)
                .inflate(R.layout.loading_spinner, this, true)
    }
}
