package com.example.filmes.presentation.fragment.viewpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.filmes.R
import com.example.filmes.databinding.FragmentViewPageBinding
import com.example.filmes.presentation.fragment.viewpage.fragment.favorite.FavoritoFragment
import com.example.filmes.presentation.fragment.viewpage.fragment.popular.PopularFragment
import com.google.android.material.tabs.TabLayoutMediator

class ViewPageFragment : Fragment(R.layout.fragment_view_page) {

    private lateinit var binding: FragmentViewPageBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentViewPageBinding.bind(view)
        setupTabLayout()
    }

    private fun setupTabLayout() {
        binding.apply {
            val fragmentList = arrayListOf(PopularFragment(), FavoritoFragment())
            val pageAdapter = ViewPageAdapter(fragmentList, childFragmentManager, lifecycle)
            movieViewPage.adapter = pageAdapter

            tabLayout.tabSelectedIndicator
            TabLayoutMediator(tabLayout, movieViewPage){ tab, position ->
                when(position){
                    0 -> tab.text = "Populares"
                    1 -> tab.text = "Favoritos"
                }
            }.attach()
        }
    }
}