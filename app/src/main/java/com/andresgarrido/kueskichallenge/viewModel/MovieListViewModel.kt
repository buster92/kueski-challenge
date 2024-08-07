package com.andresgarrido.kueskichallenge.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andresgarrido.kueskichallenge.data.MoviesRepository
import com.andresgarrido.kueskichallenge.data.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private var currentPage = 1

    private val _lastPageMovies = MutableLiveData<List<Movie>>()
    val lastPageMovies: LiveData<List<Movie>> = _lastPageMovies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchNextPage() {
        viewModelScope.launch {
            try {
                val result = repository.getAllMovies(currentPage + 1)
                currentPage = result.page
                _lastPageMovies.value = result.results
            } catch (e: Exception) {
                _error.postValue("Error when retrieving movies: ${e.localizedMessage}")
            }
        }
    }
}