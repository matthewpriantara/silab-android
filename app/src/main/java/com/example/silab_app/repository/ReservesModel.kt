package com.example.silab_app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silab_app.models.CreateReserves
import com.example.silab_app.models.ReserveInformation
import com.example.silab_app.models.request.CreateReservesRequest

class ReservesModel(
    private val repository: ReservesRepository
): ViewModel() {
    private val _reserves = MutableLiveData<ReserveInformation>()
    val reserves: LiveData<ReserveInformation> = _reserves
    private val _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage
    private val _isSuccessfull = MutableLiveData<Boolean>()
    val isSuccessfull: LiveData<Boolean> = _isSuccessfull

    fun getDataReserves(id: Int) {
        repository.getDataReserves( id ,{ data ->
            _reserves.postValue(data)
        }, { message ->
            _errMessage.postValue(message)
        })
    }
    fun postReserves(data: List<CreateReserves>){
        val body = CreateReservesRequest(data)
        repository.postReserves(body, { data ->
            if (data.count !== 0){
                _isSuccessfull.postValue(true)
            }
        }, { message ->
            _isSuccessfull.postValue(false)
            _errMessage.postValue(message)
        })
    }
}