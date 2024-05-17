package com.example.movieapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapplication.R
import com.example.movieapplication.api.ApiClient
import com.example.movieapplication.databinding.FragmentDetailMovieBinding
import com.example.movieapplication.model.response.MovieDetailResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailMovieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailMovieFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentDetailMovieBinding
    private val args: DetailMovieFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMovieBinding.inflate(layoutInflater, container, false)
        ApiClient.instance.getMovieDetail(movieId = args.id.toString()).enqueue(object :
            Callback<MovieDetailResponse> {
            override fun onResponse(
                call: Call<MovieDetailResponse>,
                response: Response<MovieDetailResponse>
            ) {
                if (response.isSuccessful) {
                    var json = Gson().toJson(response.body())
                    var data = Gson().fromJson(json, MovieDetailResponse::class.java)
                    binding.tvTitle.text = data.title
                    binding.tvOverview.text = data.overview
                    Glide.with(binding.root)
                        .load("https://image.tmdb.org/t/p/w500${data.poster_path}")
                        .into(binding.imageDetailMovie)
                    binding.imageDetailMovie.background = null
                }
            }

            override fun onFailure(call: Call<MovieDetailResponse>, t: Throwable) {
                Log.d("SIMPLE_TAG", t.message.toString())
            }
        })
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DetailMovieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}