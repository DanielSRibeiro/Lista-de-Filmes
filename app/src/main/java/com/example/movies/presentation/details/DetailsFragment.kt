package com.example.movies.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.databinding.FragmentDetailsBinding
import com.example.movies.domain.model.Movie
import com.example.movies.util.Utils
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()
    private val detailsViewModel: DetailsViewModel by viewModel()

    private lateinit var currentMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentMovie = args.movie
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(binding.root).load(currentMovie.backdropUrl).into(binding.imgMovieDetails)
        binding.txtMovieNoteDetails.text = getString(R.string.label_note, currentMovie.note.toString())
        binding.txtMovieTitleDetails.text = currentMovie.title
        binding.txtMovieDescriptionDetails.text = currentMovie.overview
        binding.txtMovieDateDetails.text =
            getString(R.string.label_realese_data, Utils.showDate(currentMovie.releaseData))

        setListeners()
        setObservers()

        detailsViewModel.check(currentMovie)
        detailsViewModel.getCategories(currentMovie)
    }


    private fun setListeners() {
        binding.backNavigation.setOnClickListener { findNavController().navigateUp() }
        binding.floatingSaveDetails.setOnClickListener {
            detailsViewModel.checkState(currentMovie)
        }
    }

    private fun setObservers() {
        detailsViewModel.categoriesNames.observe(requireActivity()) {
            binding.txtMovieGenreDetails.text = it
        }
        detailsViewModel.checkState.observe(requireActivity()) { state ->
            binding.floatingSaveDetails.isSelected = state
        }
    }
}