package com.example.filmes.presentation.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmes.R
import com.example.filmes.presentation.view.favorite.FavoritoFragment
import com.example.filmes.presentation.view.popular.PopularFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupTabLayout()
    }

    private fun setupTabLayout() {
        var pageAdapter = ViewPageAdapter(arrayListOf(PopularFragment(), FavoritoFragment()), supportFragmentManager, lifecycle)
        movie_viewPage.adapter = pageAdapter

        tab_layout.tabSelectedIndicator
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
}