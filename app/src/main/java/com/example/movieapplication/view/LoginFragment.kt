package com.example.movieapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapplication.R
import com.example.movieapplication.databinding.FragmentLoginBinding
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
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentLoginBinding
    private lateinit var userRepository: UserRepository
    private val viewModel: AuthViewModel by activityViewModels {
        AuthViewModelFactory.getInstance(requireContext())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getAllUsers().observe(viewLifecycleOwner) {
            Log.d("LoginFragment", "onCreateView: $it")
        }
        userRepository = UserRepository(requireContext())
        binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        val navController = findNavController()
        binding.tvNotHaveAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            navController.navigate(action)
        }
        binding.btnLogin.setOnClickListener {

            if (binding.etEmail.text.toString()
                    .isNotEmpty() && binding.etPassword.text.toString().isNotEmpty()
            ) {
                var checkLogin: User? =
                    login(binding.etEmail.text.toString(), binding.etPassword.text.toString())
                if (checkLogin != null) {
                    lifecycleScope.launch {
                        userRepository.saveUser(checkLogin)
                    }
                    var bundle = Bundle()
                    bundle.putSerializable("user", "$checkLogin")
                    val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                    var intent = Intent(requireContext(), HomeFragment::class.java)
                    intent.putExtra("user", bundle)
                    Log.d("LoginFragment", "onCreateView: $checkLogin")

                    navController.navigate(
                        action
                    )
                } else {
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }


        }
        return binding.root
    }

    private fun login(email: String, password: String): User? {
        return viewModel.login(email, password)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}