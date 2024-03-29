package com.v_petr.qrandbarcodescanner.ui.scanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.v_petr.qrandbarcodescanner.databinding.FragmentScannerBinding
import com.v_petr.qrandbarcodescanner.ui.history.HistoryFragment
import com.v_petr.qrandbarcodescanner.utils.CaptureAct
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

    private var backPressedTime: Long = 0
    private val BACK_PRESS_INTERVAL = 2000 // 2 секунди


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
        binding.vm = viewModel
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
        viewModel.partDescription.observe(viewLifecycleOwner) {
            when (it) {
                is UiState.Loading -> {
                    binding.textInputLayoutPartCode.isErrorEnabled = false
                }

                is UiState.Failure -> {
                    binding.textInputLayoutPartCode.error = it.error
                    binding.textInputLayoutPartCode.isHelperTextEnabled = false
                }

                is UiState.Success -> {
                    binding.textInputLayoutPartCode.isErrorEnabled = false
                    binding.textInputLayoutPartCode.helperText = it.data.toString()
                }
            }
        }

        binding.textInputLayoutPartCode.editText?.doAfterTextChanged { inputText ->
            Log.d(
                TAG,
                "onViewCreated: binding.textInputLayoutPartCode.editText?.doOnTextChanged inputText =$inputText,"
            )
            if (inputText?.length == 0) {
                binding.textInputLayoutPartCode.isErrorEnabled = false
            } else {
                viewModel.getPart(binding.run { textInputLayoutPartCode.editText?.text.toString() })
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (System.currentTimeMillis() - backPressedTime < BACK_PRESS_INTERVAL) {
                // Якщо минуло менше BACK_PRESS_INTERVAL між натисканнями, виходимо з програми
                activity?.finish()
            } else {
                Toast.makeText(requireContext(), "Натисніть ще раз для виходу", Toast.LENGTH_SHORT)
                    .show()
                backPressedTime = System.currentTimeMillis()
            }
        }
    }

    private fun enableButtons(state: Boolean) {
        binding.buttonSave.isEnabled = state
        binding.buttonSaveAndClear.isEnabled = state
    }

    private fun clearFields() {
        viewModel.clearFieldsCurrentRecord()
        binding.textInputLayoutPartCode.isErrorEnabled = false
        binding.textInputLayoutPartCode.isHelperTextEnabled = false
    }

    private fun save() {
        viewModel.addMaterialIssueRecord()
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