package com.v_petr.qrandbarcodescanner

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.v_petr.qrandbarcodescanner.databinding.FragmentHistoryOldBinding

class HistoryFragment : Fragment() {
    companion object {
        private const val TAG = "HistoryFragment"
    }

    private var _binding: FragmentHistoryOldBinding? = null
    private val binding get() = _binding!!
    private lateinit var fastenerIssuanceLog: FastenerIssuanceLog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHistoryOldBinding.inflate(inflater, container, false)
        val view = binding.root
        fastenerIssuanceLog = FastenerIssuanceLog("testpart", "testqr", 1)
        binding.test = fastenerIssuanceLog
        Log.d(TAG, "onCreateView: ")
        Log.d(TAG, "onViewCreated: $fastenerIssuanceLog")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.testButton.setOnClickListener {
            fastenerIssuanceLog.materialCode = "newmaterial"
            Log.d(TAG, "onViewCreated: $fastenerIssuanceLog")
        }
    }
}