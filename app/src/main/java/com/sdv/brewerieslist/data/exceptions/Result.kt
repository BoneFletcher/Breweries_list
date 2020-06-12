package com.sdv.brewerieslist.data.exceptions

/**
 * Created by FrostEagle on 21.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

sealed class Result<out T : Any>
class Success<out T : Any>(val data: T) : Result<T>()
class Error(val exception: Throwable, val message: String? = exception.localizedMessage) : Result<Nothing>()