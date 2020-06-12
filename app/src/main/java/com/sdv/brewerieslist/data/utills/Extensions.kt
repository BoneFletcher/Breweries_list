package com.sdv.brewerieslist.data.utills

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.sdv.brewerieslist.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

/**
 * Created by FrostEagle on 20.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

fun TextView.setInfoWithEmptyCheck(title: String?, value: String?) {
    if (value.isNullOrEmpty()) {
        visibility = GONE
    } else {
        visibility = VISIBLE
        title?.length?.let {
            val spannable = SpannableString("$title $value")
            spannable.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.colorBreweriesTitle)),
                it, spannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            text = spannable
        }
    }
}
fun View.visibleOrGone(visible: Boolean) {
    visibility = if(visible) VISIBLE else GONE
}
fun <T> Flow<T>.doOnError(onError: (Result<Throwable>) -> Unit): Flow<T> {
    return flow {
        try {
            collect { value ->
                emit(value)
            }
        } catch (e: Exception) {
            onError(Result.failure(e))
            throw e
        }
    }
}