package com.v_petr.qrandbarcodescanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.v_petr.qrandbarcodescanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root

        setContentView(root)

    }
}