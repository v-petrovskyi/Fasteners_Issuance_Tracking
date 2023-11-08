package com.v_petr.qrandbarcodescanner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.v_petr.qrandbarcodescanner.databinding.FragmentScannerBinding
import com.v_petr.qrandbarcodescanner.viewmodel.ScannerViewModel

class ScannerFragment : Fragment() {

    companion object {
        fun newInstance() = ScannerFragment()
        private const val TAG = "ScannerFragment"
    }

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ScannerViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Use the ViewModel
        val fastenerIssuanceLogLiveData = viewModel.getCurrentFastenerIssuanceLog()
        fastenerIssuanceLogLiveData.observe(viewLifecycleOwner) { binding.logRecord = it }
        binding.buttonPlus.setOnClickListener { viewModel.increaseQty() }
        binding.buttonMinus.setOnClickListener { viewModel.decreaseQty() }
    }
}