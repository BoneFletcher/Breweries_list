package com.sdv.brewerieslist.ui.breweries

import androidx.lifecycle.*
import com.sdv.brewerieslist.data.breweries.Breweries
import com.sdv.brewerieslist.data.breweries.PER_PAGE
import com.sdv.brewerieslist.data.breweries.repository.BreweriesRepository
import com.sdv.brewerieslist.ui.base.SingleLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by FrostEagle on 19.03.2020.
 * My Email: denisshakhov21@gmail.com
 * Skype: lifeforlight
 */
class BreweriesViewModel(private val breweriesRepository: BreweriesRepository): ViewModel() {
    var breweriesLoadLiveData: MutableLiveData<List<Breweries>> = MutableLiveData()
    var breweriesSearchLoadLiveData: MutableLiveData<String> = MutableLiveData()
    val progressUpdateLiveData: SingleLiveData<Boolean> = SingleLiveData()

    init {
        refresh()
    }

    private fun getBreweries(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
                kotlin.runCatching {
                    progressUpdateLiveData.postValue(true)
                    breweriesRepository.getAllBreweries(page, PER_PAGE)
                }.onSuccess {
                    progressUpdateLiveData.postValue(false)
                    breweriesLoadLiveData.postValue(it)
                }.onFailure {
                    progressUpdateLiveData.postValue(false)
                    breweriesLoadLiveData.postValue(breweriesRepository.getAllLocalBreweries())
                }
            }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    val searchLiveData = breweriesSearchLoadLiveData.asFlow()
        .debounce(300)
        .filter {
            it.trim().isEmpty().not()
        }.distinctUntilChanged()
        .flatMapLatest {
            progressUpdateLiveData.postValue(true)
            breweriesRepository.searchRepo(it).asFlow()
        }.catch{
            progressUpdateLiveData.postValue(false)
        }.asLiveData()

    fun refresh(){
        getBreweries(0)
    }

}