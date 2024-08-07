package com.andresgarrido.kueskichallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.andresgarrido.kueskichallenge.R
import com.andresgarrido.kueskichallenge.databinding.FragmentMovieListBinding
import com.andresgarrido.kueskichallenge.ui.adapter.MovieListAdapter
import com.andresgarrido.kueskichallenge.viewModel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val viewModel: MovieListViewModel by viewModels()
    private val binding get() = _binding!!
    private val adapter = MovieListAdapter()

    lateinit var mainMenu: Menu

    // Infinite scrolling variables
    lateinit var layoutManager: LinearLayoutManager
    var isLoading: Boolean = true
    var pastItemsVisible: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        val view = binding.root
        initViews()
        setupObservers()

        viewModel.fetchNextPage()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                mainMenu = menu
                menuInflater.inflate(R.menu.movie_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Switching visibility when list mode or grid mode are selected
                menuItem.setVisible(false)
                when (menuItem.itemId) {
                    R.id.action_grid_view -> {
                        mainMenu.findItem(R.id.action_list_view).setVisible(true)
                        layoutManager = GridLayoutManager(context, 2)
                        binding.recyclerView.layoutManager = layoutManager

                    }
                    R.id.action_list_view -> {
                        mainMenu.findItem(R.id.action_grid_view).setVisible(true)
                        layoutManager = LinearLayoutManager(context)
                        binding.recyclerView.layoutManager = layoutManager
                    }
                }
                return true
            }
        }, viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun initViews() {
        adapter.itemClicked.observe(viewLifecycleOwner) {
            val action = MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(it)
            view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
        }
        binding.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
            recyclerView.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (isLoading) return

                    if (dy > 0) {
                        visibleItemCount = layoutManager.childCount
                        totalItemCount = layoutManager.getItemCount()
                        pastItemsVisible = layoutManager.findFirstVisibleItemPosition()

                        if ((visibleItemCount + pastItemsVisible) >= totalItemCount) {
                            isLoading = true
                            viewModel.fetchNextPage()
                        }
                    }
                }
            })
        }
    }

    private fun setupObservers() {
        viewModel.lastPageMovies.observe(viewLifecycleOwner) { data ->
            adapter.setData(data.toTypedArray())
            isLoading = false
        }
        viewModel.error.observe(viewLifecycleOwner) { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            isLoading = false
        }
    }
}