package com.example.filmes.presentation.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmes.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupTabLayout()
    }

    private fun setupTabLayout() {
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
}