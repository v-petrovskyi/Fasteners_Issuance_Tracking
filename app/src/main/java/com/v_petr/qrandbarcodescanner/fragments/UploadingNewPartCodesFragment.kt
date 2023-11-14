package com.v_petr.qrandbarcodescanner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.v_petr.qrandbarcodescanner.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadingNewPartCodesFragment : Fragment() {

    companion object {
        fun newInstance() = UploadingNewPartCodesFragment()
    }

    private lateinit var viewModel: UploadingNewPartCodesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_uploading_new_part_codes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(UploadingNewPartCodesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}