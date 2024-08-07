package com.andresgarrido.kueskichallenge.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.andresgarrido.kueskichallenge.R
import com.andresgarrido.kueskichallenge.data.model.Movie
import com.andresgarrido.kueskichallenge.databinding.MovieListItemBinding

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {
    private var dataset = mutableListOf<Movie>()
    val baseImageUrl = "https://image.tmdb.org/t/p/w500"

    private val _itemClicked = MutableLiveData<Int>()
    val itemClicked: LiveData<Int> = _itemClicked


    class MyViewHolder(
        val binding: MovieListItemBinding,
        private val onItemClicked: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClicked(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding) { position ->
            _itemClicked.value = position
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = dataset[position]
        holder.binding.apply {
            movieItemTitle.text = item.originalTitle
            movieItemImage.load("$baseImageUrl${item.posterPath}")
            movieItemReleaseDate.text = context.getString(R.string.release_date, item.releaseDate)
            movieItemVoteAverage.text = context.getString(R.string.vote_average, item.voteAverage)
        }
    }

    override fun getItemCount() = dataset.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: Array<Movie>) {
        dataset.addAll(data)
        notifyItemRangeInserted(dataset.size - data.size, data.size)
    }
}