package com.v_petr.qrandbarcodescanner.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.v_petr.qrandbarcodescanner.R
import com.v_petr.qrandbarcodescanner.databinding.FragmentProfileBinding
import com.v_petr.qrandbarcodescanner.ui.auth.AuthViewModel
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
        private const val TAG = "ProfileFragment"
    }

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getUser()
        makeUiElementsEditable(false)
        FirebaseAuth.getInstance().currentUser?.displayName
        observe()
        binding.textInputLayoutProfileEmail.editText?.isEnabled = false
        binding.imageButtonEdit.setOnClickListener {
            makeUiElementsEditable(true)
        }
        binding.imageButtonDone.setOnClickListener {
            makeUiElementsEditable(false)
            profileViewModel.updateUser(
                firstName = binding.textInputLayoutProfileFirstName.editText?.text.toString(),
                lastName = binding.textInputLayoutProfileLastName.editText?.text.toString()
            )
        }
        binding.buttonLogout.setOnClickListener {
            authViewModel.logout {
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }
    }

    private fun observe() {
        profileViewModel.currentUser.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBarProfile.visibility = View.VISIBLE
                    blockUI(true)
                }

                is UiState.Failure -> {
                    binding.progressBarProfile.visibility = View.GONE
                    blockUI(false)
                }

                is UiState.Success -> {
                    binding.progressBarProfile.visibility = View.GONE
                    binding.textInputLayoutProfileFirstName.editText?.setText(state.data.firstName)
                    binding.textInputLayoutProfileLastName.editText?.setText(state.data.lastName)
                    binding.textInputLayoutProfileEmail.editText?.setText(state.data.email)
                    blockUI(false)
                }
            }
        }

        profileViewModel.updateUser.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBarProfile.visibility = View.VISIBLE
                    blockUI(true)
                }

                is UiState.Failure -> {
                    binding.progressBarProfile.visibility = View.GONE
                    profileViewModel.getUser()
                    blockUI(false)
                }

                is UiState.Success -> {
                    binding.progressBarProfile.visibility = View.GONE
                    Toast.makeText(context, state.data, Toast.LENGTH_SHORT).show()
                    blockUI(false)
                }
            }

        }
    }

    private fun blockUI(b: Boolean) {
        binding.buttonLogout.isEnabled = !b
    }

    private fun makeUiElementsEditable(b: Boolean) {
        if (b) {
            binding.imageButtonDone.visibility = View.VISIBLE
            binding.imageButtonEdit.visibility = View.GONE
        } else {
            binding.imageButtonDone.visibility = View.GONE
            binding.imageButtonEdit.visibility = View.VISIBLE
        }
        binding.textInputLayoutProfileFirstName.editText?.isEnabled = b
        binding.textInputLayoutProfileLastName.editText?.isEnabled = b
    }
}