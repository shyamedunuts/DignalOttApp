package com.dignalott.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dignalott.models.Content
import com.dignalott.models.Movies
import com.dignalott.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: MovieRepository) : ViewModel() {


    private val _moviesList = MutableLiveData<Movies>()
    val moviesList : LiveData<Movies>
    get()=_moviesList

    var secondLoaded = false
    var thirdLoaded = false

    fun getMovieList(context:Context) = viewModelScope.launch(Dispatchers.IO) {
        val result=repository.getMoviesList(context,"CONTENTLISTINGPAGE-PAGE1.json")
        withContext(Dispatchers.Main)
        {
            _moviesList.value=result
        }

    }

    fun loadSecond(context:Context) = viewModelScope.launch(Dispatchers.IO) {
        val result=repository.getMoviesList(context = context,"CONTENTLISTINGPAGE-PAGE2.json")
        result?.page?.`content-items`?.content?.forEach {
            (_moviesList.value?.page?.`content-items`?.content as ArrayList<Content>).add(it)
        }
        secondLoaded=true
    }
    fun loadThird(context:Context) = viewModelScope.launch(Dispatchers.IO) {
        val result=repository.getMoviesList(context = context,"CONTENTLISTINGPAGE-PAGE3.json")
        result?.page?.`content-items`?.content?.forEach {
            (_moviesList.value?.page?.`content-items`?.content as ArrayList<Content>).add(it)
        }
        thirdLoaded=true
    }

}