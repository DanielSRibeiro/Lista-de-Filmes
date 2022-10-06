package com.example.filmes.presentation.fragment.details

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.filmes.R
import com.example.filmes.databinding.FragmentDetailsBinding
import com.example.filmes.databinding.FragmentViewPageBinding
import com.example.filmes.domain.model.MovieDto
import com.example.filmes.presentation.MainActivity
import com.example.filmes.presentation.fragment.LocalViewModel
import com.example.filmes.utilis.BASE_IMAGEM
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

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
                    localViewModel.input.deleteMovie(movie.id)
                else
                    localViewModel.input.insertMovie(movie, dataString.toString())
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

            txtMovieNoteDetails.text = "${movie.notaMedia}/10 \nAvaliação"
            txtMovieTitleDetails.text = movie.tituloFilme
            txtMovieDescriptionDetails.text = movie.sinopse
            txtMovieDateDetails.text =
                if (dataString == null)
                    realeseDate
                else
                    "Lançamento: $dataString"
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