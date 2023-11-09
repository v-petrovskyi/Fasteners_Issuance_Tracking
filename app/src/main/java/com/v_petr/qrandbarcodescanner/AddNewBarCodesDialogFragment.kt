package com.v_petr.qrandbarcodescanner

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import org.apache.poi.ss.usermodel.WorkbookFactory
import java.io.FileDescriptor
import java.io.FileInputStream


class AddNewBarCodesDialogFragment : DialogFragment() {

    companion object {
        private const val TAG = "AddNewBarCodesDialogFragment"
    }


    private lateinit var database: FirebaseDatabase
    private lateinit var barcodesReference: DatabaseReference

    private lateinit var fileXlxBarcodes: FileDescriptor


    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                getFileDescriptorFromURI(uri)
            }
            uri?.let { returnUri ->
                requireActivity().contentResolver.query(returnUri, null, null, null, null)
            }?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                val fileName = cursor.getString(nameIndex)
                val fileSize = cursor.getLong(sizeIndex)
//                binding.textViewSelectedFileName.text = buildString {
//                    append(fileName).append(" ").append(fileSize)
//                }
            }
        }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database =
            Firebase.database("https://fastener-issuance-log-default-rtdb.europe-west1.firebasedatabase.app")

        barcodesReference = database.reference.child("BarCodes")

//        binding.buttonGetLast.setOnClickListener {
//            getLastBarcodeInDatabase()
//        }
//        binding.buttonSelectFile.setOnClickListener {
//            getContent.launch("application/vnd.ms-excel")
//        }
//        binding.buttonCleanBarcodes.setOnClickListener {
//            barcodesReference.setValue(null)
//        }
//
//        binding.buttonReadXLS.setOnClickListener {
//            if (fileXlxBarcodes != null) {
//                val barCodeList = readXLS(fileXlxBarcodes)
//
//                barCodeList.forEach {
//                    barcodesReference.child(it.barCode.toString())
//                        .setValue(it)
//                }
//            }
//        }

    }

    private fun getFileDescriptorFromURI(uri: Uri) {
        val parcelFileDescriptor =
            requireContext().contentResolver.openFileDescriptor(uri, "r")
        if (parcelFileDescriptor != null) {
            fileXlxBarcodes = parcelFileDescriptor.fileDescriptor
        }

    }

    private fun getLastBarcodeInDatabase() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (child in dataSnapshot.children) {
                    val value = child.getValue<PartCode>()
                    if (value != null) {
//                        binding.textViewLastElement.text = value.barCode.toString()
                    }
                    Log.d(TAG, "onDataChange: $value")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        val lastValue = barcodesReference
            .orderByKey()
            .limitToLast(1)
            .addListenerForSingleValueEvent(postListener)
        Log.d(TAG, "getLastBarcodeInDatabase: $lastValue")
    }

    private fun readXLS(fileDescriptor: FileDescriptor): List<PartCode> {
        val list = mutableListOf<PartCode>()

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

                list.add(PartCode(owner, order, barCode))

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