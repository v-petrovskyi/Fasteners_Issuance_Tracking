package com.v_petr.qrandbarcodescanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.v_petr.qrandbarcodescanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root

        setContentView(root)

        val scannerFragment = ScannerFragment()
        val lastScannedItemsFragment = LastScannedItemsFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(scannerFragment)
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.scanner -> setCurrentFragment(scannerFragment)
                R.id.list -> setCurrentFragment(lastScannedItemsFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragment.id, fragment)
            commit()
        }
}