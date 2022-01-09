package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.domain.usecase.remote.MovieUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface InputMovieViewModel{
    fun getAllMovies(name:String?)
}

interface OutputMovieViewModel{
    val movieList: LiveData<ArrayList<MovieDto>>
    val error: LiveData<Boolean>
}

interface ContractMovieViewModel : InputMovieViewModel, OutputMovieViewModel

class MovieViewModel(
    private var getMovie: MovieUseCase
) : ViewModel(), ContractMovieViewModel {

    val input: InputMovieViewModel = this
    val output: OutputMovieViewModel = this

    private val mMovieList = MutableLiveData<ArrayList<MovieDto>>()
    override val movieList: LiveData<ArrayList<MovieDto>>
        get() = mMovieList

    private val mError = MutableLiveData<Boolean>()
    override val error: LiveData<Boolean>
        get() = mError

    override fun getAllMovies(name:String?){
        CoroutineScope(Dispatchers.Main).launch {
            var resultsMovies = withContext(Dispatchers.Default) {
                    getMovie.invoke(name)
                }

            if(!resultsMovies.movieList.isNullOrEmpty())
                mMovieList.value = resultsMovies.movieList
            else
                mError.value = name == null
        }
    }

}