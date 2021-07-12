package com.example.filmes.presentation.view

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.filmes.R
import com.example.filmes.data.api.RetrofitTask
import com.example.filmes.data.repository.MovieImplementation
import com.example.filmes.presentation.view.adapter.ViewPageAdapter
import com.example.filmes.presentation.viewModel.MovieViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val movieViewModel:MovieViewModel by viewModel()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        setSupportActionBar(toolbar_main)
        ConfigTabLayout()
    }

    private fun ConfigTabLayout() {
        tab_layout.tabSelectedIndicator
        var pageAdapter = ViewPageAdapter(supportFragmentManager, lifecycle)
        movie_viewPage.adapter = pageAdapter

        TabLayoutMediator(tab_layout, movie_viewPage){ tab, position ->
            when(position){
                0 -> {
                    tab.text = "Populares"
                }
                1 -> {
                    tab.text = "Favoritos"
                }
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val search = menu!!.findItem(R.id.searchBar)
        val searchView:SearchView = search.actionView as SearchView
        searchView.queryHint = "Buscar movie"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                PopularFragment().searchMovie(newText+"")
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }
}