package com.v_petr.qrandbarcodescanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.v_petr.qrandbarcodescanner.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {
    companion object {
        private const val TAG = "HistoryFragment"
    }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.d(TAG, "onCreateView: ")
        return view
    }
}