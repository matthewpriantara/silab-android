package com.example.silab_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silab_app.R
import com.example.silab_app.adapters.EquipmentAdapter
import com.example.silab_app.models.Equipment

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EquipmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        recyclerView = view.findViewById(R.id.rvEquipment)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val equipmentList = listOf(
            Equipment("Osiloskop Analog GW Instek GOS 620", "Lab RPL", "Done"),
            Equipment("Osiloskop Analog GW Instek GOS 620", "Lab RPL", "Done"),
            Equipment("Osiloskop Analog GW Instek GOS 620", "Lab RPL", "Done"),
            Equipment("Osiloskop Analog GW Instek GOS 620", "Lab RPL", "Done"),
            Equipment("Osiloskop Analog GW Instek GOS 620", "Lab RPL", "Done"),
            Equipment("Osiloskop Analog GW Instek GOS 620", "Lab RPL", "Done")
        )

        adapter = EquipmentAdapter(equipmentList)
        recyclerView.adapter = adapter

        return view
    }
}