package com.example.silab_app

import InventoryAdapter
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.silab_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = listOf(
            InventoryItem("Osiloskop Analog GW Instek GDS 620", "Lab RPL", "Available", R.drawable.osiloscope),
            InventoryItem("Osiloskop Digital XYZ", "Lab RPL", "Not Available", R.drawable.osiloscope),
            InventoryItem("Osiloskop Analog Gizi", "Lab RPL", "Available", R.drawable.osiloscope),
            InventoryItem("Osiloskop Digital QWERTY", "Lab RPL", "Not Available", R.drawable.osiloscope)
        )

        val adapter = InventoryAdapter(data)

        binding.recyclerInventory.layoutManager =
            GridLayoutManager(this, 2)

        binding.recyclerInventory.adapter = adapter
import androidx.fragment.app.Fragment
import com.example.silab_app.fragments.HistoryFragment
import com.example.silab_app.fragments.HomeFragment
import com.example.silab_app.fragments.ProfileFragment
import com.example.silab_app.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check login status
        sessionManager = SessionManager(this)
        if (!sessionManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNav)

        // Set default fragment
        loadFragment(HomeFragment())

        bottomNav.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.nav_history -> {
                    loadFragment(HistoryFragment())
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}