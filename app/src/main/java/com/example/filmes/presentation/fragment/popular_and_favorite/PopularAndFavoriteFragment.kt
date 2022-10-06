package com.example.filmes.presentation.fragment.popular_and_favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import com.example.filmes.R
import com.example.filmes.databinding.FragmentPopularAndFavoriteBinding
import com.example.filmes.presentation.fragment.popular_and_favorite.screens.favorite.FavoritoFragment
import com.example.filmes.presentation.fragment.popular_and_favorite.screens.popular.PopularFragment
import com.example.filmes.utilis.IOnAction
import com.google.android.material.tabs.TabLayoutMediator

class PopularAndFavoriteFragment : Fragment() {

    private var _binding: FragmentPopularAndFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPopularAndFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val search = menu.findItem(R.id.action_bar_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = "Pesquisar Filmes"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val fragment = childFragmentManager.findFragmentByTag("f${binding.movieViewPage.currentItem}")
                    (fragment as? IOnAction)?.executeAction(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {

                return true
            }
        })
    }

    private fun setupTabLayout() {
        binding.apply {
            val fragmentList = listOf(
                PopularFragment(),
                FavoritoFragment()
            )
            val pageAdapter = SectionsPagerAdapter(fragmentList, childFragmentManager, lifecycle)
            movieViewPage.adapter = pageAdapter

            tabLayout.tabSelectedIndicator
            TabLayoutMediator(tabLayout, movieViewPage) { tab, position ->
                when (position) {
                    0 -> tab.text = "Populares"
                    1 -> tab.text = "Favoritos"
                }
            }.attach()
        }
    }
}