package com.v_petr.qrandbarcodescanner


import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.v_petr.qrandbarcodescanner.databinding.ActivityMainBinding
import com.v_petr.qrandbarcodescanner.viewmodel.ScannerViewModel

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ScannerViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel.createNewFastenerIssuanceLog()

        val view = binding.root
        setContentView(view)
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigationView
//        setupWithNavController(bottomNavigationView, navController)
        bottomNavigationView.setupWithNavController(navController)
        val navigationDrawer = binding.navigationDrawer
        navigationDrawer.setupWithNavController(navController)

    }

}