package com.v_petr.qrandbarcodescanner

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.v_petr.qrandbarcodescanner.databinding.FragmentAddNewBarcodesBinding
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileDescriptor
import java.io.FileInputStream


class AddNewBarCodesDialogFragment : DialogFragment() {

    companion object {
        private const val TAG = "AddNewBarCodesDialogFragment"
    }

    private var _binding: FragmentAddNewBarcodesBinding? = null

    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase

    private lateinit var uriBarcodesXLS: Uri

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                uriBarcodesXLS = uri
                binding.textViewSelectedFileName.text = uri.toString()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewBarcodesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database =
            Firebase.database("https://fastener-issuance-log-default-rtdb.europe-west1.firebasedatabase.app")

        val reference = database.reference


        binding.buttonGetLast.setOnClickListener {
            val postListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (child in dataSnapshot.children) {
                        val value = child.getValue<BarCode>()
                        Log.d(TAG, "onDataChange: $value")
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {

                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }

            val lastValue = reference.child("BarCodes")
                .orderByKey()
                .limitToLast(1)
                .addListenerForSingleValueEvent(postListener)

            Log.d(TAG, "onViewCreated: $lastValue")

        }
        binding.buttonSelectFile.setOnClickListener {
            getContent.launch("application/vnd.ms-excel")
        }
        binding.buttonCleanBarcodes.setOnClickListener {
            database.reference.child("BarCodes").setValue(null)
        }

        binding.buttonReadXLS.setOnClickListener {
            Log.d(TAG, "onViewCreated: buttonReadXLS filePath = $uriBarcodesXLS")
            val parcelFileDescriptor =
                requireContext().contentResolver.openFileDescriptor(uriBarcodesXLS, "r")

            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
            if (fileDescriptor != null) {
                val barCodeList = readXLS(fileDescriptor)


                barCodeList.forEach {
                    database.reference.child("BarCodes").child(it.barCode.toString())
                        .setValue(it)
                }
            }
        }

    }
    private fun readXLS(fileDescriptor: FileDescriptor): List<BarCode> {
        val list = mutableListOf<BarCode>()

        try {
            val inputStream = FileInputStream(fileDescriptor)
            val workbook = WorkbookFactory.create(inputStream)

            // Вибираємо аркуш (sheet) (наприклад, перший аркуш)
            val sheet = workbook.getSheetAt(0)
            Log.d(TAG, "readXLS: ${sheet.lastRowNum}")

            for (rowIndex in 1..sheet.lastRowNum) {
                val currentRow = sheet.getRow(rowIndex)
                val owner = currentRow.getCell(0).toString()
                val order = currentRow.getCell(1).toString()
                val barCode = currentRow.getCell(2).toString().toLong()

                list.add(BarCode(owner, order, barCode))

            }

            inputStream.close()
            Log.d(TAG, "readXLS: $inputStream")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

}