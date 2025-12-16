package com.example.silab_app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silab_app.models.InventoryItem
import com.example.silab_app.R
import InventoryAdapter
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.silab_app.FormActivity
import com.example.silab_app.databinding.FragmentHistoryBinding
import com.example.silab_app.models.response.InventoryItemResponse
import com.example.silab_app.network.ApiClient
import com.example.silab_app.network.ApiService
import com.example.silab_app.repository.HistoryRepository
import com.example.silab_app.repository.HistoryViewModel
import com.example.silab_app.repository.HomeViewModel
import com.example.silab_app.repository.InventoryRepository
import com.example.silab_app.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class HistoryFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding

    private lateinit var sessionManager: SessionManager

    private lateinit var client: ApiService

    private lateinit var viewModel: HistoryViewModel

    private val inventoryList = ArrayList<InventoryItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sessionManager = SessionManager(requireContext())
        client = ApiClient.getInstance(sessionManager.getToken())
        val repository = HistoryRepository(client)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HistoryViewModel(repository) as T
            }
        })[HistoryViewModel::class.java]

        val adapter = InventoryAdapter(inventoryList) { item ->
//            val intent = Intent(requireContext(), FormActivity::class.java)
//            intent.putExtra("id", item.id)
//            intent.putExtra("name", item.name)
//            intent.putExtra("location", item.location)
//            intent.putExtra("status", item.status)
//            intent.putExtra("image", item.imgRes)
//            startActivity(intent)
        }

        binding.rvEquipment.layoutManager =
            GridLayoutManager(requireContext(), 2)

        binding.rvEquipment.adapter = adapter

        viewModel.inventories.observe(viewLifecycleOwner) { data ->
            Log.d("FRAGMENT", "Jumlah data: ${data.size}")
            inventoryList.clear()
            inventoryList.addAll(data)
            Log.d("Data", inventoryList.toString())
            adapter.notifyDataSetChanged()
        }

        viewModel.errMessage.observe(viewLifecycleOwner) { data ->
            Toast.makeText(requireContext(), "Error: ${data}", Toast.LENGTH_SHORT).show()
        }

        viewModel.fetchInventories()
    }
}

