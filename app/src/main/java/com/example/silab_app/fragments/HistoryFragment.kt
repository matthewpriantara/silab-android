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
import android.widget.Toast
import com.example.silab_app.models.response.InventoryItemResponse
import com.example.silab_app.network.ApiClient
import com.example.silab_app.network.ApiService
import com.example.silab_app.utils.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class HistoryFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: InventoryAdapter
    private lateinit var sessionManager: SessionManager
    private lateinit var client: ApiService


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        sessionManager = SessionManager(requireContext())
        client = ApiClient.getInstance(sessionManager.getToken())
        recyclerView = view.findViewById(R.id.rvEquipment)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // DATA sama seperti Home
        val data = loadInventory()

        adapter = InventoryAdapter(data)
        recyclerView.adapter = adapter

        return view
    }

    private fun loadInventory(): List<InventoryItem>{
        val data = ArrayList<InventoryItem>()
        client.getALlHistory().enqueue(object: Callback<InventoryItemResponse>{
            override fun onResponse(
                p0: Call<InventoryItemResponse?>,
                p1: Response<InventoryItemResponse?>
            ) {
                if(!p1.isSuccessful) {
                    Toast.makeText(requireContext(),
                        "Gagal Mendapatkan Data: HTTP ${p1.message()}",
                        Toast.LENGTH_SHORT).show()
                    return
                }
                val response = p1.body()!!.data
                for (i in response) {
                    data.add(i)
                }
            }

            override fun onFailure(
                p0: Call<InventoryItemResponse?>,
                p1: Throwable
            ) {
                Toast.makeText(requireContext(), "Error: ${p1.message}", Toast.LENGTH_SHORT).show()
                return
            }
        })
        return data
    }
}
