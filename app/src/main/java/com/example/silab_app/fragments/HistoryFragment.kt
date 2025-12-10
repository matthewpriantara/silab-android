package com.example.silab_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silab_app.InventoryItem
import com.example.silab_app.R
import InventoryAdapter

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InventoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.rvEquipment)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // DATA sama seperti Home
        val data = listOf(
            InventoryItem("Osiloskop Analog GW Instek GDS 620", "Lab RPL", "Done", R.drawable.osiloscope),
            InventoryItem("Osiloskop Digital XYZ", "Lab RPL", "Done", R.drawable.osiloscope),
            InventoryItem("Osiloskop Analog Gizi", "Lab RPL", "Done", R.drawable.osiloscope),
            InventoryItem("Osiloskop Digital QWERTY", "Lab RPL", "Done", R.drawable.osiloscope)
        )

        adapter = InventoryAdapter(data)
        recyclerView.adapter = adapter

        return view
    }
}
