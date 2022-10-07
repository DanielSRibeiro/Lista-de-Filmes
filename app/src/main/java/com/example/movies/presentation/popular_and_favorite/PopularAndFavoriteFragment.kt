package com.example.movies.presentation.popular_and_favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import com.example.movies.R
import com.example.movies.databinding.FragmentPopularAndFavoriteBinding
import com.example.movies.presentation.popular_and_favorite.screens.favorite.FavoriteFragment
import com.example.movies.presentation.popular_and_favorite.screens.popular.PopularFragment
import com.example.movies.util.IOnAction
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class PopularAndFavoriteFragment : Fragment() {

    private var _binding: FragmentPopularAndFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PopularAndFavoriteViewModel by viewModel()

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
        viewModel.getAllCategories()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val search = menu.findItem(R.id.action_bar_search)
        val searchView = search?.actionView as SearchView
        searchView.queryHint = getString(R.string.label_search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val fragment =
                        childFragmentManager.findFragmentByTag("f${binding.movieViewPage.currentItem}")
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
        val fragmentList = listOf(
            PopularFragment(),
            FavoriteFragment()
        )
        val pageAdapter = SectionsPagerAdapter(fragmentList, childFragmentManager, lifecycle)
        binding.movieViewPage.adapter = pageAdapter

        binding.tabLayout.tabSelectedIndicator
        TabLayoutMediator(binding.tabLayout, binding.movieViewPage) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.title_populars)
                1 -> tab.text = getString(R.string.title_favorites)
            }
        }.attach()
    }
}