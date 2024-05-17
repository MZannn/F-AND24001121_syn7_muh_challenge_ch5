package com.example.movieapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movieapplication.R
import com.example.movieapplication.adapter.MovieAdapter
import com.example.movieapplication.api.ApiClient
import com.example.movieapplication.databinding.FragmentHomeBinding
import com.example.movieapplication.model.response.MovieResponse
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
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        var recyclerView = binding.rvMovies
        ApiClient.instance.getMovieNowPlaying().enqueue(object : Callback<MovieResponse> {

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    var json = Gson().toJson(response.body())
                    var data = Gson().fromJson(json, MovieResponse::class.java)

                    val adapter = MovieAdapter(data.results) { movie ->
                        Log.d("HomeFragment", "Movie clicked: ${movie.title}")
                        val action = HomeFragmentDirections
                            .actionHomeFragmentToDetailMovieFragment(movie.id.toString())
                        findNavController().navigate(action)
                    }
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = adapter

                } else {
                    Log.e("HomeFragment", "Response is not successful: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                Log.d("SIMPLE_TAG", "error ${t.message}")
            }

        })

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}