package com.v_petr.qrandbarcodescanner

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.v_petr.qrandbarcodescanner.databinding.FragmentAddNewBarcodesBinding


class AddNewBarCodesDialogFragment : DialogFragment() {

    companion object {
        private const val TAG = "AddNewBarCodesDialogFragment"
    }

    private var _binding: FragmentAddNewBarcodesBinding? = null

    private val binding get() = _binding!!
    private lateinit var database: FirebaseDatabase

    private lateinit var uriBarcodesXLS: Uri

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
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                }
            }



            val lastValue = reference.child("BarCodes")
                .orderByKey()
                .limitToLast(1)
                .addListenerForSingleValueEvent(postListener)




            Log.d(TAG, "onViewCreated: $lastValue")

//            reference.child("users").child("26")
//                .setValue(FastenerIssuanceLog("some part", "some material", 5))
//            reference.child("users").push()
//                .setValue(FastenerIssuanceLog("push part", "push material", 5))
//            val key = reference.child("users").push().key
//            reference.child("users").push()

//            Log.d(TAG, "onViewCreated: $key")
        }
    }

}