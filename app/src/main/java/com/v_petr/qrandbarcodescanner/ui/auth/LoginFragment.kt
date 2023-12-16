package com.v_petr.qrandbarcodescanner.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.v_petr.qrandbarcodescanner.R
import com.v_petr.qrandbarcodescanner.databinding.FragmentLoginBinding
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    companion object {
        const val TAG = "LoginFragment"
        fun newInstance() = LoginFragment()
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.textViewGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.textViewForgotPasswort.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }

        binding.loginButton.setOnClickListener {
            if (validation()) {
                viewModel.login(
                    email = binding.textInputLayoutEmail.editText?.text.toString(),
                    password = binding.textInputLayoutPassword.editText?.text.toString()
                )
            }
        }
    }

    private fun observer() {
        viewModel.login.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBarLogin.visibility = View.VISIBLE
                    binding.loginButton.isEnabled = false
                }

                is UiState.Failure -> {
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                    binding.progressBarLogin.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                }

                is UiState.Success -> {
                    binding.progressBarLogin.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment_to_scannerFragment)
                }
            }
        }

    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.textInputLayoutEmail.editText?.text.isNullOrEmpty()) {
            isValid = false
            binding.textInputLayoutEmail.error = getString(R.string.field_cant_be_empty)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(
                binding.textInputLayoutEmail.editText?.text.toString()
            ).matches()
        ) {
            isValid = false
            binding.textInputLayoutEmail.error = getString(R.string.input_correct_email)
        } else {
            binding.textInputLayoutEmail.isErrorEnabled = false
        }
        if (binding.textInputLayoutPassword.editText?.text.isNullOrEmpty()) {
            isValid = false
            binding.textInputLayoutPassword.error = getString(R.string.field_cant_be_empty)
        } else {
            if (binding.textInputLayoutPassword.editText?.text.toString().length < 6) {
                isValid = false
                binding.textInputLayoutPassword.error = getString(R.string.min_length_pass)
            } else {
                binding.textInputLayoutPassword.isErrorEnabled = false
            }
        }
        return isValid
    }

}