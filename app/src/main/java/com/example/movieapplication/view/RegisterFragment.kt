package com.example.movieapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapplication.R
import com.example.movieapplication.databinding.FragmentRegisterBinding
import com.example.movieapplication.model.User
import com.example.movieapplication.viewModel.AuthViewModel
import com.example.movieapplication.viewModel.AuthViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory.getInstance(requireContext())
    }
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

        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        val navController = findNavController()
        binding.btnRegister.setOnClickListener {

            val user = User(
                email = binding.etEmail.text.toString(),
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString()
            )
            var confirmPasswordIsSame =
                binding.etPassword.text.toString() == binding.etConfirmPassword.text.toString()
            if (binding.etUsername.text.toString().isNotEmpty() && binding.etEmail.text.toString()
                    .isNotEmpty() && binding.etPassword.text.toString()
                    .isNotEmpty() && confirmPasswordIsSame
            ) {
                var checkUser = viewModel.register(user)
                if (checkUser != null) {
                    navController.popBackStack()
                } else {
                    Toast.makeText(requireContext(), "$checkUser", Toast.LENGTH_SHORT).show()
                }
            } else if (!confirmPasswordIsSame) {
                Toast.makeText(
                    requireContext(),
                    "Password and Confirm Password is not same",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(requireContext(), "Please fill all the fields", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}