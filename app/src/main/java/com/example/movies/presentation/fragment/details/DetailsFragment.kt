package com.example.movies.presentation.fragment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movies.BuildConfig
import com.example.movies.databinding.FragmentDetailsBinding
import com.example.movies.data.network.model.MovieResponseDto
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.MainActivity
import com.example.movies.presentation.fragment.LocalViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()
    private val localViewModel: LocalViewModel by viewModel()
    private val detailsViewModel: DetailsViewModel by viewModel()

    private lateinit var movie: Movie
    private var dataString:String? = null
    private var paraDeleta = false
    private var realeseDate = ""

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
        initView()
    }

    private fun initView() {
        binding.apply {
            setupMovie()
            setupFavorito()
            setupCategories()

            floatingSaveDetails.setOnClickListener {
                if(paraDeleta)
                    localViewModel.deleteMovie(movie.id)
                else
                    localViewModel.insertMovie(movie, dataString.toString())
            }

            backNavigation.setOnClickListener {
                (activity as MainActivity).supportActionBar?.show()
                findNavController().popBackStack()
            }

            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
                (activity as MainActivity).supportActionBar?.show()
                findNavController().popBackStack()
            }
        }
    }

    private fun setupMovie() {
        binding.apply {
            movie = args.movie
            dataString = args.releaseDate

            localViewModel.verificar(movie.id)

            Glide.with(binding.root)
                .load(BuildConfig.BASE_IMAGEM + movie.backdropPath)
                .into(imgMovieDetails)

            txtMovieNoteDetails.text = "${movie.note}/10 \nAvaliação"
            txtMovieTitleDetails.text = movie.title
            txtMovieDescriptionDetails.text = movie.overview
            txtMovieDateDetails.text =
                if (dataString == null)
                    realeseDate
                else
                    "Lançamento: $dataString"
        }
    }

    private fun setupFavorito() {
        localViewModel.verificado.observe(requireActivity()) { foiSalvo ->
            this.paraDeleta = foiSalvo
            binding.floatingSaveDetails.isSelected = foiSalvo
        }
    }

    private fun setupCategories(){
        detailsViewModel.getCategories(movie)
        detailsViewModel.categories.observe(requireActivity()) { genres ->
            binding.txtMovieGenreDetails.text = genres
        }
    }
}