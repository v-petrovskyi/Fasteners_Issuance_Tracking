package com.v_petr.qrandbarcodescanner

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.v_petr.qrandbarcodescanner.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private val TAG = "ProfileFragment"
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase
    private lateinit var uriBarcodesXLS: Uri



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        Log.d(TAG, "onCreateView: ")
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView: ")
        _binding = null
    }


    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database =
            Firebase.database("https://fastener-issuance-log-default-rtdb.europe-west1.firebasedatabase.app")

        val reference = database.reference

//        binding.buttonGetLast.setOnClickListener {
//            val postListener = object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    for (child in dataSnapshot.children) {
//                        val value = child.getValue<BarCode>()
//                        Log.d(TAG, "onDataChange: $value")
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    // Getting Post failed, log a message
//                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
//                }
//            }
//
//
//
//            val lastValue = reference.child("BarCodes")
//                .orderByKey()
//                .limitToLast(1)
//                .addListenerForSingleValueEvent(postListener)
//
//
//
//
//            Log.d(TAG, "onViewCreated: $lastValue")
//
////            reference.child("users").child("26")
////                .setValue(FastenerIssuanceLog("some part", "some material", 5))
////            reference.child("users").push()
////                .setValue(FastenerIssuanceLog("push part", "push material", 5))
////            val key = reference.child("users").push().key
////            reference.child("users").push()
//
////            Log.d(TAG, "onViewCreated: $key")
//        }
//        binding.buttonUpdate.setOnClickListener {
//            reference.child("users").child("25").child("materialCode").setValue("updated material")
//        }
//        binding.buttonGet.setOnClickListener {
//            reference.child("users").child("25").get().addOnSuccessListener {
//                Log.i("firebase", "Got value ${it.value}")
//            }.addOnFailureListener {
//                Log.e("firebase", "Error getting data", it)
//            }
//        }


//        binding.buttonGetUri.setOnClickListener {
//            getContent.launch("application/vnd.ms-excel")
//        }
//        binding.buttonCleanBarcodes.setOnClickListener {
//            database.reference.child("BarCodes").setValue(null)
//        }
//
//        binding.buttonReadXLS.setOnClickListener {
//            Log.d(TAG, "onViewCreated: buttonReadXLS filePath = $uriBarcodesXLS")
//            val parcelFileDescriptor =
//                requireContext().contentResolver.openFileDescriptor(uriBarcodesXLS, "r")
//
//            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
//            if (fileDescriptor != null) {
//                val barCodeList = readXLS(fileDescriptor)
//
//
//                barCodeList.forEach {
//                    database.reference.child("BarCodes").child(it.barCode.toString())
//                        .setValue(it)
//                }
//            }
//        }




        Log.d(TAG, "onViewCreated: ")
    }

//    private fun readXLS(fileDescriptor: FileDescriptor): List<BarCode> {
//        val list = mutableListOf<BarCode>()
//
//        try {
//            val inputStream = FileInputStream(fileDescriptor)
//            val workbook = WorkbookFactory.create(inputStream)
//
//            // Вибираємо аркуш (sheet) (наприклад, перший аркуш)
//            val sheet = workbook.getSheetAt(0)
//            Log.d(TAG, "readXLS: ${sheet.lastRowNum}")
//
//            for (rowIndex in 1..sheet.lastRowNum) {
//                val currentRow = sheet.getRow(rowIndex)
//                val owner = currentRow.getCell(0).toString()
//                var order = currentRow.getCell(1).toString()
//                var barCode = currentRow.getCell(2).toString().toLong()
//
//                list.add(BarCode(owner, order, barCode))
//
//            }
//
//            inputStream.close()
//            Log.d(TAG, "readXLS: $inputStream")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return list
//    }

}