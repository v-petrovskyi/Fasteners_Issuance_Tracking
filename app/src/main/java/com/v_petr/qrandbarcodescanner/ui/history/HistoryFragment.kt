package com.v_petr.qrandbarcodescanner.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.v_petr.qrandbarcodescanner.R
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

        observe()
        val itemTouchHelper = ItemTouchHelper(
            SwipeCallback(
                requireContext(),
                // delete case
                onSwipedLeftListener = { position ->
                    context?.let { context ->
                        MaterialAlertDialogBuilder(context)
                            .setTitle(resources.getString(R.string.delete))
                            .setMessage(resources.getString(R.string.are_you_sure_you_want_to_remove))
                            .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                                // Respond to negative button press
                                adapter.notifyItemChanged(position)
                            }
                            .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                                // Respond to positive button press
                                Log.d(TAG, "onViewCreated: onSwipedLeftListener $position")
                                viewModel.delete(adapter.getItem(position))
                                adapter.deleteItem(position)
                            }
                            .show()
                    }

                },
                // update case
                onSwipedRightListener = { position ->
                    Log.d(TAG, "onViewCreated: onSwipedRightListener $position")
                })
        )
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun observe() {
        viewModel.records.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Failure -> {
                    Log.e(TAG, "viewModel.records.observe: Failure ${state.error}")
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
        viewModel.delete.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is UiState.Failure -> {
                    Log.e(TAG, "viewModel.delete.observe: ${state.error}")
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    viewModel.getAllRecords()
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}