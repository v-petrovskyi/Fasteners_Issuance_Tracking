package com.v_petr.qrandbarcodescanner.ui.scanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.v_petr.qrandbarcodescanner.CaptureAct
import com.v_petr.qrandbarcodescanner.R
import com.v_petr.qrandbarcodescanner.data.model.MaterialIssueRecord
import com.v_petr.qrandbarcodescanner.databinding.FragmentScannerBinding
import com.v_petr.qrandbarcodescanner.ui.history.HistoryFragment
import com.v_petr.qrandbarcodescanner.utils.EventObserver
import com.v_petr.qrandbarcodescanner.utils.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
        binding.buttonScanMaterial.setOnClickListener {
            materialScan.launch(scanQrCode())
        }
        binding.buttonScanPart.setOnClickListener {
            partScan.launch(scanQrCode())
        }
        binding.buttonSave.setOnClickListener {
            if (validate()) {
                save()
            }
        }
        binding.buttonSaveAndClear.setOnClickListener {
            if (validate()) {
                save()
                clearFields()
            }
        }

        viewModel.addRecord.observe(viewLifecycleOwner, EventObserver { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    enableButtons(false)
                }

                is UiState.Failure -> {
                    Log.e(HistoryFragment.TAG, "onViewCreated: ${state.error}")
                    Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                    enableButtons(true)
                }

                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    enableButtons(true)
                }
            }
        })
        binding.textInputLayoutPartCode.editText?.doOnTextChanged { inputText, _, _, _ ->
            if (inputText?.length != 0 && inputText?.length!! < 13) {
                binding.textInputLayoutPartCode.error = resources.getString(R.string.checkPartCode)
            } else {
                binding.textInputLayoutPartCode.error = ""
            }
        }
    }

    private fun enableButtons(state: Boolean) {
        binding.buttonSave.isEnabled = state
        binding.buttonSaveAndClear.isEnabled = state
    }

    private fun clearFields() {
        binding.textInputLayoutPartCode.editText?.setText("")
        binding.textInputLayoutMaterialCode.editText?.setText("")
        binding.textInputLayoutQty.editText?.setText(0)

    }

    private fun save() {
        viewModel.addMaterialIssueRecord(
            MaterialIssueRecord(
                part = binding.textInputLayoutPartCode.editText?.text.toString(),
                material = binding.textInputLayoutMaterialCode.editText?.text.toString(),
                qty = binding.textInputLayoutQty.editText?.text.toString().toInt()
            )
        )

    }

    private fun validate(): Boolean {
        if (binding.textInputLayoutPartCode.editText?.text.toString() == "") {
            Toast.makeText(context, "Input Part", Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.textInputLayoutMaterialCode.editText?.text.toString() == "") {
            Toast.makeText(context, "Input Material", Toast.LENGTH_SHORT).show()
            return false
        } else {
            try {
                if (binding.textInputLayoutQty.editText?.text.toString().toInt() <= 0) {
                    Toast.makeText(context, "Input Qty > 0", Toast.LENGTH_SHORT).show()
                    return false
                }
            } catch (ex: NumberFormatException) {
                Toast.makeText(context, "its not number", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun scanQrCode(): ScanOptions {
        val options = ScanOptions()
        options.setPrompt("Volume up to flash on")
        options.setBeepEnabled(true)
        options.setOrientationLocked(true)
        options.captureActivity = CaptureAct::class.java
        return options
    }

    private val partScan = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            binding.textInputLayoutPartCode.editText?.setText(it.contents)
        }
    }

    private val materialScan = registerForActivityResult(ScanContract()) {
        if (it.contents != null) {
            binding.textInputLayoutMaterialCode.editText?.setText(it.contents)
        }
    }
}