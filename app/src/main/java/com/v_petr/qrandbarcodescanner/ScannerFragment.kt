package com.v_petr.qrandbarcodescanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.v_petr.qrandbarcodescanner.databinding.FragmentScannerBinding

class ScannerFragment : Fragment() {
    private val TAG = "ScannerFragment"
    private var _binding: FragmentScannerBinding? = null
    private var currentQty: Int = 0


    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.d(TAG, "onCreateView: ")
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.scanButton.setOnClickListener {
            scanCode()
        }

        binding.buttonMinus.setOnClickListener {
            try {
                currentQty = binding.editTextNumber.text.toString().toInt()
            } catch (ex: NumberFormatException) {
                Toast.makeText(activity, ex.toString(), Toast.LENGTH_SHORT).show()
            }
            currentQty--
            if (currentQty < 0) {
                binding.editTextNumber.setText("0")
                Toast.makeText(activity, "Кількість не може бути менше 0", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.editTextNumber.setText(currentQty.toString())
            }
        }

        binding.buttonPlus.setOnClickListener {
            try {
                currentQty = binding.editTextNumber.text.toString().toInt()
            } catch (ex: NumberFormatException) {
                Toast.makeText(activity, ex.toString(), Toast.LENGTH_SHORT).show()
            }
            currentQty++
            binding.editTextNumber.setText(currentQty.toString())
        }
        Log.d(TAG, "onViewCreated: ")
    }

    private fun scanCode() {
        val options = ScanOptions()
        options.setPrompt("Volume up to flash on")
        options.setBeepEnabled(true)
//        options.setOrientationLocked(true)
        options.captureActivity = CaptureAct::class.java
//        options.captureActivity = CaptureAct::class.java
        barLauncher.launch(options)
    }

    private val barLauncher = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            binding.tvScanResult.text = it.contents
        }
    }


    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d(TAG, "onViewStateRestored: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: ")
    }

}