package com.example.filmes.presentation.fragment.viewpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmes.R
import com.example.filmes.presentation.fragment.viewpage.fragment.favorite.FavoritoFragment
import com.example.filmes.presentation.fragment.viewpage.fragment.popular.PopularFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_page.*

class ViewPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
    }

    private fun setupTabLayout() {
        var fragmentList = arrayListOf(PopularFragment(), FavoritoFragment())
        var pageAdapter = ViewPageAdapter(fragmentList, childFragmentManager, lifecycle)
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