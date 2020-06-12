package com.sdv.brewerieslist.di

import com.sdv.brewerieslist.ui.breweries.BreweriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by FrostEagle on 19.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */

    val viewModelModule = module {
        viewModel{BreweriesViewModel(get())}
    }
