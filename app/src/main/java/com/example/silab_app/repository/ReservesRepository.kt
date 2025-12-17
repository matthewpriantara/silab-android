package com.example.silab_app.repository

import android.util.Log
import com.example.silab_app.models.CreateReserves
import com.example.silab_app.models.InventoryItem
import com.example.silab_app.models.ReserveInformation
import com.example.silab_app.models.request.CreateReservesRequest
import com.example.silab_app.models.response.CountResponse
import com.example.silab_app.models.response.CreateReservesResponse
import com.example.silab_app.models.response.InventoryItemResponse
import com.example.silab_app.models.response.ReservesInformationResponse
import com.example.silab_app.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class ReservesRepository(
    private val api: ApiService
) {
    fun getDataReserves(id:Int, onSuccess: (ReserveInformation) -> Unit, onError: (String) -> Unit) {
        val data = ArrayList<InventoryItem>()
        api.getDetailInformation(id).enqueue(object: Callback<ReservesInformationResponse>{
            override fun onResponse(
                p0: Call<ReservesInformationResponse?>,
                p1: Response<ReservesInformationResponse?>
            ) {
                if(!p1.isSuccessful) {
                    onError(p1.message())
                    return
                }
                val response = p1.body()!!.data
                onSuccess(response)
            }

            override fun onFailure(
                p0: Call<ReservesInformationResponse?>,
                p1: Throwable
            ) {
//                Toast.makeText(requireContext(), "Error: ${p1.message}", Toast.LENGTH_SHORT).show()
                onError(p1.message ?: "Terjadi Kesalahan")
            }
        })
        Log.d("Info", data.toString())
    }

    fun postReserves(data: CreateReservesRequest, onSuccess: (CountResponse) -> Unit, onError: (String) -> Unit){
        val body = data
        api.createReserves(body).enqueue(object : Callback<CreateReservesResponse>{
            override fun onResponse(
                p0: Call<CreateReservesResponse?>,
                p1: Response<CreateReservesResponse?>
            ) {
                if(!p1.isSuccessful) {
                    onError(p1.message())
                    return
                }
                val response = p1.body()!!.data
                onSuccess(response)
            }

            override fun onFailure(
                p0: Call<CreateReservesResponse?>,
                p1: Throwable
            ) {
                onError(p1.message ?: "Terjadi Kesalahan")
            }
        })
    }
}