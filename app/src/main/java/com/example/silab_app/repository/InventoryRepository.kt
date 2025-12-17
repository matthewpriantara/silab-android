package com.example.silab_app.repository

import android.util.Log
import android.widget.Toast
import com.example.silab_app.models.InventoryItem
import com.example.silab_app.models.response.InventoryItemResponse
import com.example.silab_app.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class InventoryRepository(
    private val api: ApiService
) {
    fun getInventoriesData(onSuccess: (List<InventoryItem>) -> Unit, onError: (String) -> Unit) {
        val data = ArrayList<InventoryItem>()
        api.getAllInventories().enqueue(object: Callback<InventoryItemResponse>{
            override fun onResponse(
                p0: Call<InventoryItemResponse?>,
                p1: Response<InventoryItemResponse?>
            ) {
                if(!p1.isSuccessful) {
                    onError(p1.message())
                    return
                }
                val response = p1.body()!!.data
                onSuccess(response)
            }

            override fun onFailure(
                p0: Call<InventoryItemResponse?>,
                p1: Throwable
            ) {
//                Toast.makeText(requireContext(), "Error: ${p1.message}", Toast.LENGTH_SHORT).show()
                onError(p1.message ?: "Terjadi Kesalahan")
            }
        })
        Log.d("Info", data.toString())
    }
}