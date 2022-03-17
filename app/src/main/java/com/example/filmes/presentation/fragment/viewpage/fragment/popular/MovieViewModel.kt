package com.example.filmes.presentation.fragment.viewpage.fragment.popular

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmes.R
import com.example.filmes.domain.model.ResultsMoviesDto
import com.example.filmes.domain.usecase.remote.MovieUseCase
import com.example.filmes.presentation.fragment.state.GetMoviesState
import com.example.filmes.presentation.fragment.state.GetMoviesState.*
import com.example.filmes.utilis.NetworkChecker
import com.example.filmes.utilis.TAG_MOVIE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

interface InputMovieViewModel {
    fun getAllMovies(name: String?)
}

interface OutputMovieViewModel {
    val getMoviesState: LiveData<GetMoviesState>
}

interface ContractMovieViewModel : InputMovieViewModel, OutputMovieViewModel

class MovieViewModel(
    private var context: Context,
    private var getMovie: MovieUseCase
) : ViewModel(), ContractMovieViewModel {

    val input: InputMovieViewModel = this
    val output: OutputMovieViewModel = this

    private var _getMoviesState = MutableLiveData<GetMoviesState>()
    override val getMoviesState: LiveData<GetMoviesState>
        get() = _getMoviesState

    override fun getAllMovies(name: String?) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                var connectivityManager = ContextCompat.getSystemService(
                    context,
                    ConnectivityManager::class.java
                ) as ConnectivityManager

                if (NetworkChecker(connectivityManager).hasInternet()) {
                    val resultsMovies: ResultsMoviesDto? = withContext(Dispatchers.Default) {
                        getMovie.invoke(name)
                    }

                    if (resultsMovies?.movieList.isNullOrEmpty())
                        _getMoviesState.value = ErrorNotSearch("Não encontramos nenhum filme com esse nome")
                    else
                        _getMoviesState.value = Success(resultsMovies!!)
                } else {
                    _getMoviesState.value =
                        ErrorNetwork(context.getString(R.string.error_conection))
                }
            } catch (e: Exception) {
                _getMoviesState.value = ErrorNetwork(context.getString(R.string.error_conection))
                Log.d(TAG_MOVIE, "movieUseCase: $e")
            }
        }
    }

}