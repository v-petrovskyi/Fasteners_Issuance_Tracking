package com.v_petr.qrandbarcodescanner.ui.auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.v_petr.qrandbarcodescanner.R
import com.v_petr.qrandbarcodescanner.databinding.FragmentForgotPasswordBinding
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {
    companion object {
        const val TAG = "ForgotPasswordFragment"
        fun newInstance() = LoginFragment()
    }

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.sendButton.setOnClickListener {
            if (validation()) {
                viewModel.forgotPassword(
                    email = binding.textInputLayoutEmail.editText?.text.toString()
                )
            }
        }
    }

    private fun observer() {
        viewModel.forgotPassword.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBarSend.visibility = View.VISIBLE
                    binding.sendButton.isEnabled = false
                }

                is UiState.Failure -> {
                    Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
                    binding.progressBarSend.visibility = View.GONE
                    binding.sendButton.isEnabled = true
                }

                is UiState.Success -> {
                    binding.progressBarSend.visibility = View.GONE
                    binding.sendButton.isEnabled = true
                    Toast.makeText(context, state.data, Toast.LENGTH_SHORT).show()
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
        return isValid
    }
}