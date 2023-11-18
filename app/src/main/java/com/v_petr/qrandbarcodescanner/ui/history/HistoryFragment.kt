package com.v_petr.qrandbarcodescanner.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.v_petr.qrandbarcodescanner.databinding.FragmentHistoryBinding
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    companion object {
        const val TAG = "HistoryFragment"
        fun newInstance() = HistoryFragment()
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by activityViewModels()
    private val adapter by lazy { MaterialItemAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        viewModel.getAllRecords()
        viewModel.records.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Failure -> {
                    Log.e(TAG, "onViewCreated: ${state.error}")
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    adapter.updateList(state.data.toMutableList())
                    adapter.notifyDataSetChanged()
                }
            }
        }

    }
}