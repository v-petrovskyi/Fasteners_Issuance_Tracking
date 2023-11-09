package com.v_petr.qrandbarcodescanner


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.v_petr.qrandbarcodescanner.databinding.ActivityMainBinding
import com.v_petr.qrandbarcodescanner.fragments.ScannerFragment
import com.v_petr.qrandbarcodescanner.viewmodel.ScannerViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ScannerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel.createNewFastenerIssuanceLog()

        val view = binding.root
        setContentView(view)
        val scannerFragment = ScannerFragment.newInstance()
        val historyFragment = HistoryFragment()
        val profileFragment = ProfileFragment()
        setCurrentFragment(scannerFragment)

        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.scanner -> setCurrentFragment(scannerFragment)
                R.id.history -> setCurrentFragment(historyFragment)
                R.id.profile -> setCurrentFragment(profileFragment)
            }
            true
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.add_new_barcodes_menu -> {
                    val dialogFragment = AddNewBarCodesDialogFragment()
                    dialogFragment.show(supportFragmentManager, "Add new barcodes")

                    true
                }

                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(binding.flFragment.id, fragment)
            commit()
        }


}