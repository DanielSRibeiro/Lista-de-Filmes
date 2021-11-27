package com.example.filmes.presentation.view.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.filmes.presentation.view.favorite.FavoritoFragment
import com.example.filmes.presentation.view.popular.PopularFragment

class ViewPageAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                 PopularFragment()
            }
            else ->{
                FavoritoFragment()
            }
        }
    }
}