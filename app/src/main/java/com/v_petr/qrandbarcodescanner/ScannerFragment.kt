package com.v_petr.qrandbarcodescanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.v_petr.qrandbarcodescanner.databinding.FragmentScannerBinding

class ScannerFragment : Fragment() {
    companion object {
        private const val TAG = "ScannerFragment"
    }

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

        binding.buttonScanPart.setOnClickListener {
            scanBarCode()
        }
        binding.buttonScanMaterial.setOnClickListener {
            scanQrCode()
        }

        binding.buttonSave.setOnClickListener {
            save();
        }
        binding.buttonSaveAndClear.setOnClickListener {
            save()
            clearFields()
        }

        binding.buttonMinus.setOnClickListener {
            try {
                currentQty = binding.editTextQty.text.toString().toInt()
            } catch (ex: NumberFormatException) {
                Toast.makeText(activity, ex.toString(), Toast.LENGTH_SHORT).show()
            }
            currentQty--
            if (currentQty < 0) {
                binding.editTextQty.setText("0")
                Toast.makeText(activity, "Кількість не може бути менше 0", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.editTextQty.setText(currentQty.toString())
            }
        }

        binding.buttonPlus.setOnClickListener {
            try {
                currentQty = binding.editTextQty.text.toString().toInt()
            } catch (ex: NumberFormatException) {
                Toast.makeText(activity, ex.toString(), Toast.LENGTH_SHORT).show()
            }
            currentQty++
            binding.editTextQty.setText(currentQty.toString())
        }
        Log.d(TAG, "onViewCreated: ")
    }

    private fun clearFields() {
        binding.editTextQty.setText("0")
        binding.editTextPartBarcode.setText("")
        binding.editTextMaterialCode.setText("")
    }

    private fun save() {
        val database =
            Firebase.database("https://fastener-issuance-log-default-rtdb.europe-west1.firebasedatabase.app")
        val myRef = database.getReference("FastenerIssuanceLog")
        if (currentQty <= 0) {
            Toast.makeText(context, "qty is 0 or less", Toast.LENGTH_SHORT).show()
        }
        var record = FastenerIssuanceLog(
            binding.editTextPartBarcode.text.toString(),
            binding.editTextMaterialCode.text.toString(),
            currentQty
        )
        Log.d(TAG, "save: $record")
        myRef.push().setValue(record)
    }

    private fun scanBarCode() {
        val options = ScanOptions()
        options.setPrompt("Volume up to flash on")
        options.setBeepEnabled(true)
//        options.setOrientationLocked(true)
        options.captureActivity = CaptureAct::class.java
        barLauncher.launch(options)
    }

    private val barLauncher = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            binding.editTextPartBarcode.setText(it.contents)
        }
    }

    private fun scanQrCode() {
        val options = ScanOptions()
        options.setPrompt("Volume up to flash on")
        options.setBeepEnabled(true)
//        options.setOrientationLocked(true)
        options.captureActivity = CaptureAct::class.java
        qrLauncher.launch(options)
    }

    private val qrLauncher = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            binding.editTextMaterialCode.setText(it.contents)
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