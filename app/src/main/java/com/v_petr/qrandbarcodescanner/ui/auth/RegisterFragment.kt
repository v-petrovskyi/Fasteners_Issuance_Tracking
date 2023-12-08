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
import com.v_petr.qrandbarcodescanner.data.model.User
import com.v_petr.qrandbarcodescanner.databinding.FragmentRegisterBinding
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    companion object {
        const val TAG = "RegisterFragment"
        fun newInstance() = RegisterFragment()
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        binding.registerButton.setOnClickListener {
            if (validation()) {
                viewModel.register(
                    email = binding.textInputLayoutEmail.editText?.text.toString(),
                    password = binding.textInputLayoutPassword.editText?.text.toString(),
                    user = getUser()
                )
            }
        }
    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.textInputLayoutFirstName.editText?.text.isNullOrEmpty()) {
            isValid = false
            binding.textInputLayoutFirstName.error = getString(R.string.field_cant_be_empty)
        } else {
            binding.textInputLayoutFirstName.isErrorEnabled = false
        }
        if (binding.textInputLayoutLastName.editText?.text.isNullOrEmpty()) {
            isValid = false
            binding.textInputLayoutLastName.error = getString(R.string.field_cant_be_empty)
        } else {
            binding.textInputLayoutLastName.isErrorEnabled = false
        }
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

    private fun observer() {
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBarRegistration.visibility = View.VISIBLE
                    binding.registerButton.isEnabled = false
                }

                is UiState.Failure -> {
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                    binding.registerButton.isEnabled = true
                    binding.progressBarRegistration.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.progressBarRegistration.visibility = View.GONE
                    binding.registerButton.isEnabled = true
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_registerFragment_to_scannerFragment)
                }
            }
        }
    }

    private fun getUser(): User {
        return User(
            id = "",
            firstName = binding.textInputLayoutFirstName.editText?.text.toString(),
            lastName = binding.textInputLayoutLastName.editText?.text.toString(),
            email = binding.textInputLayoutEmail.editText?.text.toString()
        )
    }


}