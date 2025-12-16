package com.example.silab_app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silab_app.models.InventoryItem

class HistoryViewModel(
    private val repository: HistoryRepository
) : ViewModel() {

    private val _inventories = MutableLiveData<List<InventoryItem>>()
    val inventories: LiveData<List<InventoryItem>> = _inventories

    private val _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    fun fetchInventories() {
        repository.getHistoryData(
            onSuccess = { list ->
                val historyItems = list.filter {
                    it.status != "Available" &&
                            it.status != "Not Available"
                }

                _inventories.postValue(historyItems)
            },
            onError = { msg ->
                _errMessage.postValue(msg)
            }
        )
    }
}