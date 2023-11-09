package com.v_petr.qrandbarcodescanner.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.v_petr.qrandbarcodescanner.models.FastenerIssuanceLog

class ScannerViewModel : ViewModel() {

    private lateinit var fastenerIssuanceLog: FastenerIssuanceLog
    private val fastenerIssuanceLogLiveData = MutableLiveData<FastenerIssuanceLog>()
    // TODO: Implement the ViewModel

    fun createNewFastenerIssuanceLog() {
        fastenerIssuanceLog = FastenerIssuanceLog()
    }

    fun getCurrentFastenerIssuanceLog(): MutableLiveData<FastenerIssuanceLog> {
        fastenerIssuanceLogLiveData.value = fastenerIssuanceLog
        return fastenerIssuanceLogLiveData
    }

    fun increaseQty() {
        fastenerIssuanceLog.setQty(fastenerIssuanceLog.getQty() + 1)
        fastenerIssuanceLogLiveData.value = fastenerIssuanceLog
    }

    fun decreaseQty() {
        fastenerIssuanceLog.setQty(fastenerIssuanceLog.getQty() - 1)
        fastenerIssuanceLogLiveData.value = fastenerIssuanceLog
    }

//    private lateinit var databaseReference: DatabaseReference
    /*
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.logRecord = FastenerIssuanceLog()


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
        databaseReference = database.getReference("FastenerIssuanceLog")
        var record = FastenerIssuanceLog(
            binding.editTextPartBarcode.text.toString(),
            binding.editTextMaterialCode.text.toString(),
            currentQty
        )
        if (record.qty <= 0) {

            val builder = AlertDialog.Builder(requireContext())

            builder.setMessage("Кількість становить 0. Все вірно?")

            builder.setPositiveButton("Yes") { dialog, which ->
                addRecordInDatabase(databaseReference, record)
                dialog.dismiss() // Закрити діалогове вікно
            }

            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()

        } else{
            addRecordInDatabase(databaseReference, record)
        }
        Log.d(TAG, "save: $record")
    }

    private fun addRecordInDatabase(
        myRef: DatabaseReference,
        record: FastenerIssuanceLog
    ) {
        myRef.push().setValue(record).addOnSuccessListener {
            Toast.makeText(context, "Data saved successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "ERROR!!! DATA NOT SAVED", Toast.LENGTH_LONG).show()
            Log.e(TAG, "addRecordInDatabase: ${it.stackTrace}", )
        }
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

*/


}