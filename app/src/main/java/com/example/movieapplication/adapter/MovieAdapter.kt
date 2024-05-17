package com.example.movieapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.model.response.Result
class MovieAdapter(
    private var list: List<Result>,
    private val onClick: (Result) -> Unit
) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById<CardView>(R.id.movieItem)
        val title: TextView = itemView.findViewById<TextView>(R.id.tvTitle)
        val content: TextView = itemView.findViewById<TextView>(R.id.tvContent)
        val image: ImageView = itemView.findViewById<ImageView>(R.id.ivMovieImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.title.text = item.title
        holder.content.text = item.overview
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w500${item.poster_path}")
            .into(holder.image)
        holder.cardView.setOnClickListener {
            onClick(item)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}