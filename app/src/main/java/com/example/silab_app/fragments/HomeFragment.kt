package com.example.silab_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.silab_app.InventoryItem
import com.example.silab_app.R
import com.example.silab_app.databinding.FragmentHomeBinding
import InventoryAdapter
import android.content.Intent
import com.example.silab_app.FormActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- Data dummy alat ---
        val data = listOf(
            InventoryItem("Osiloskop Analog GW Instek GDS 620", "Lab RPL", "Available", R.drawable.osiloscope),
            InventoryItem("Osiloskop Digital XYZ", "Lab RPL", "Not Available", R.drawable.osiloscope),
            InventoryItem("Osiloskop Analog Gizi", "Lab RPL", "Available", R.drawable.osiloscope),
            InventoryItem("Osiloskop Digital QWERTY", "Lab RPL", "Not Available", R.drawable.osiloscope)
        )

        // --- Setup adapter ---
        val adapter = InventoryAdapter(data) { item ->
            val intent = Intent(requireContext(), FormActivity::class.java)
            intent.putExtra("name", item.name)
            intent.putExtra("location", item.location)
            intent.putExtra("status", item.status)
            intent.putExtra("image", item.imageRes)
            startActivity(intent)
        }

        binding.recyclerInventory.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerInventory.adapter = adapter

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
