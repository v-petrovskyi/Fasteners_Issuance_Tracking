package com.v_petr.qrandbarcodescanner.ui.scanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.v_petr.qrandbarcodescanner.CaptureAct
import com.v_petr.qrandbarcodescanner.databinding.FragmentScannerBinding
import com.v_petr.qrandbarcodescanner.ui.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScannerFragment : Fragment() {

    companion object {
        fun newInstance() = ScannerFragment()
        private const val TAG = "ScannerFragment"
    }

    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
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
        viewModel.add()
        binding.buttonScanMaterial.setOnClickListener {
            materialScan.launch(scanQrCode())
        }
        binding.buttonScanPart.setOnClickListener{
            partScan.launch(scanQrCode())
        }
    }

    private fun scanQrCode() :ScanOptions {
        val options = ScanOptions()
        options.setPrompt("Volume up to flash on")
        options.setBeepEnabled(true)
//        options.setOrientationLocked(true)
        options.captureActivity = CaptureAct::class.java
        return options
    }

    private val partScan = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            Log.d(TAG, "part: ${it.contents}")
        }
    }

    private val materialScan = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            Log.d(TAG, "material: ${it.contents}")
        }
    }
}