package com.example.filmes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.filmes.presentation.view.favorite.FavoritoFragment
import com.example.filmes.presentation.view.main.ViewPageAdapter
import com.example.filmes.presentation.view.popular.PopularFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_view_page.*
import kotlinx.android.synthetic.main.fragment_view_page.view.*

class ViewPageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_view_page, container, false)

        setupTabLayout(view)

        return view
    }

    private fun setupTabLayout(view: View) {
        var fragmentList = arrayListOf(PopularFragment(), FavoritoFragment())
        var pageAdapter = ViewPageAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        view.movie_viewPage.adapter = pageAdapter

        view.tab_layout.tabSelectedIndicator
        TabLayoutMediator(view.tab_layout, view.movie_viewPage){ tab, position ->
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