package com.example.movieapplication.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapplication.R
import com.example.movieapplication.databinding.FragmentProfileBinding
import com.example.movieapplication.model.User
import com.example.movieapplication.repository.UserRepository
import com.example.movieapplication.viewModel.AuthViewModel
import com.example.movieapplication.viewModel.AuthViewModelFactory
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userRepository: UserRepository
    var userData:User? = null
    private val authViewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        userRepository = UserRepository(requireContext())
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
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        lifecycleScope.launch {
            userRepository.userFlow.collect { user ->
                user?.let {
                    userData = getUser(user.id!!)
                    binding.etUsername.setText(user.username)
                }
            }
        }
        binding.etUsername.setText(userData?.username)
        if (userData?.address != null) {
            binding.etAddress.setText(userData?.address)
        }
        if (userData?.birthdate != null) {
            binding.etBirthdate.setText(userData?.birthdate)
        }
        if (userData?.fullname != null) {
            binding?.etFullname?.setText(userData?.fullname)
        }
        binding.btnUpdate.setOnClickListener {
            var data = updateUser(
                fullname = binding.etFullname.text.toString(),
                address = binding.etAddress.text.toString(),
                birthdate = binding.etBirthdate.text.toString(),
                username = binding.etUsername.text.toString(),
                id = userData?.id!!
            )
            Log.e("data", "data ini $data")
            getUser(userData?.id!!)
        }
        binding.btnLogout.setOnClickListener {
            lifecycleScope.launch {
                userRepository.clearUser()
                userRepository.userFlow.collect { user ->
                    user?.let {
                        Log.d("user" , "${user.id}")
                    }
                }
            }
            val action = ProfileFragmentDirections.actionProfileFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        return binding.root
    }

    fun getUser(id: Int): User {
        return authViewModel.getUserById(id)
    }

    fun updateUser(
        fullname: String,
        birthdate: String,
        address: String,
        username: String,
        id: Int
    ) {
        return authViewModel.updateUser(
            username = username,
            fullname = fullname,
            birthdate = birthdate,
            address = address,
            id = id
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}