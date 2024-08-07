package com.andresgarrido.kueskichallenge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import com.andresgarrido.kueskichallenge.data.model.Movie
import com.andresgarrido.kueskichallenge.databinding.FragmentMovieDetailsBinding
import com.andresgarrido.kueskichallenge.helper.getMidSizePosterUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    val args: MovieDetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        loadMovie(args.movie)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadMovie(movie: Movie) {
        binding.apply {
            movieDetailsTitle.text = movie.originalTitle
            movieDetailsReleaseDate.text = movie.releaseDate
            movieItemVoteAverage.text = movie.voteAverage.toString()
            movieDetailsOverview.text = movie.overview
            movieDetailsImage.load(movie.getMidSizePosterUrl())
        }
    }
}
