package com.v_petr.qrandbarcodescanner.ui.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.v_petr.qrandbarcodescanner.databinding.FragmentScannerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerFragment : Fragment() {

    companion object {
        fun newInstance() = ScannerFragment()
        private const val TAG = "ScannerFragment"
    }

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

//    private val viewModel: ScannerViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Use the ViewModel
//        val fastenerIssuanceLogLiveData = viewModel.getCurrentFastenerIssuanceLog()
//        fastenerIssuanceLogLiveData.observe(viewLifecycleOwner) { binding.materialIssueRecord = it }
//        binding.buttonPlus.setOnClickListener { viewModel.increaseQty() }
//        binding.buttonMinus.setOnClickListener { viewModel.decreaseQty() }
    }
}