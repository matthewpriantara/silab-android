package com.example.silab_app

import InventoryAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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
    }
}