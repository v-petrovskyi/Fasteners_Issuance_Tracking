package com.v_petr.qrandbarcodescanner.ui

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.v_petr.qrandbarcodescanner.data.model.PartCode
import com.v_petr.qrandbarcodescanner.databinding.FragmentUploadingNewPartCodesBinding
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileInputStream

@AndroidEntryPoint
class UploadingNewPartCodesFragment : Fragment() {

    companion object {
        fun newInstance() = UploadingNewPartCodesFragment()
        const val TAG = "UploadingNewPartCodesFragment"
    }

    private var _binding: FragmentUploadingNewPartCodesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UploadingNewPartCodesViewModel by viewModels()
    private lateinit var uri2: Uri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadingNewPartCodesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonChoiceFile.setOnClickListener {
            getContent.launch("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        }
        binding.buttonUpload.setOnClickListener {
            val list: List<PartCode> = readXLS(uri2)
            viewModel.upload(list)
        }
        binding.imageButton.setOnClickListener {
            if (binding.imageView.visibility == View.GONE) {
                binding.imageView.visibility = View.VISIBLE
            } else {
                binding.imageView.visibility = View.GONE
            }
        }
        observe()
    }

    private fun observe() {
        viewModel.getSuccessCountLiveData().observe(viewLifecycleOwner) {
            binding.textView3.text = viewModel.getSuccessCount().toString()
        }
    }


    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { returnUri ->
                requireActivity().contentResolver.query(returnUri, null, null, null, null)
            }?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                cursor.moveToFirst()
                val fileName = cursor.getString(nameIndex)
                uri2 = uri
                binding.textViewFileName.text = buildString {
                    append(fileName)
                }
            }
        }


    private fun readXLS(uri: Uri): List<PartCode> {
        val list = mutableListOf<PartCode>()
        val parcelFileDescriptor =
            requireContext().contentResolver.openFileDescriptor(uri, "r")
        if (parcelFileDescriptor != null) {
            val fileDescriptor = parcelFileDescriptor.fileDescriptor
            try {
                val inputStream = FileInputStream(fileDescriptor)
                inputStream.use {
                    val workbook = XSSFWorkbook(inputStream)
                    val sheet = workbook.getSheetAt(0)
                    Log.d(TAG, "readXLS: lastRowNum  ${sheet.lastRowNum}")

                    for (rowIndex in 1..sheet.lastRowNum) {
                        val currentRow = sheet.getRow(rowIndex)
                        val owner = currentRow.getCell(0).rawValue.toString()
                        val order = currentRow.getCell(1).toString()
                        val barCode = currentRow.getCell(2).rawValue.toString()

                        list.add(PartCode(owner, order, barCode))
                    }
                }
                // Вибираємо аркуш (sheet) (наприклад, перший аркуш)

                Log.d(TAG, "readXLS: $inputStream")
            } catch (e: Exception) {
                Log.e(TAG, "readXLS: ${e.stackTrace}")
            }
            parcelFileDescriptor.close()
        }
        Log.d(TAG, "readXLS: list.size = ${list.size}")
        return list
    }

}