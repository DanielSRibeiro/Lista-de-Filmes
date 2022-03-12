package com.example.filmes.presentation.fragment.details

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.filmes.R
import com.example.filmes.databinding.FragmentDetailsBinding
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.MainActivity
import com.example.filmes.presentation.fragment.LocalViewModel
import com.example.filmes.utilis.BASE_IMAGEM
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding

    private val args: DetailsFragmentArgs by navArgs()
    private val localViewModel: LocalViewModel by viewModel()

    private lateinit var movie: MovieDto
    private var dataString:String? = null
    private var paraDeleta = false
    private var realeseDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        initView()
    }

    private fun initView() {
        binding.apply {
            setupMovie()
            setupFavorito()
            setupCategories()

            floatingSaveDetails.setOnClickListener {
                if(paraDeleta)
                    localViewModel.input.deleteMovie(movie.id)
                else
                    localViewModel.input.insertMovie(movie, realeseDate)
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
            dataString = args.dataLancamento

            localViewModel.input.verificar(movie.id)

            Glide.with(binding.root)
                .load("${BASE_IMAGEM + movie.backdropPath}")
                .into(imgMovieDetails)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            realeseDate = dateFormat.format(movie.dataLancamento)

            txtMovieNoteDetails.text = "${movie.notaMedia}/10 \nAvaliação"
            txtMovieTitleDetails.text = movie.tituloFilme
            txtMovieDescriptionDetails.text = movie.sinopse
            txtMovieDateDetails.text =
                if (dataString == null)
                    "Lançamento: $realeseDate"
                else
                    dataString
        }
    }

    private fun setupFavorito() {
        localViewModel.output.verificado.observe(requireActivity()) { foiSalvo ->
            this.paraDeleta = foiSalvo
            binding.floatingSaveDetails.isSelected = foiSalvo
        }
    }

    private fun setupCategories(){
        localViewModel.input.getCategories(movie)
        localViewModel.output.categories.observe(requireActivity()) { genres ->
            binding.txtMovieGenreDetails.text = genres
        }
    }
}