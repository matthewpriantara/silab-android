package com.example.silab_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.silab_app.R
import com.example.silab_app.models.Equipment

class EquipmentAdapter(private val equipmentList: List<Equipment>) :
    RecyclerView.Adapter<EquipmentAdapter.EquipmentViewHolder>() {

    class EquipmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvEquipmentName)
        val tvLab: TextView = itemView.findViewById(R.id.tvLabName)
        val btnStatus: Button = itemView.findViewById(R.id.btnStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipment, parent, false)
        return EquipmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipmentViewHolder, position: Int) {
        val equipment = equipmentList[position]
        holder.tvName.text = equipment.name
        holder.tvLab.text = equipment.lab
        holder.btnStatus.text = equipment.status
    }

    override fun getItemCount(): Int = equipmentList.size
}