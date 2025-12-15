package com.example.silab_app.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.silab_app.models.InventoryItem

class HomeViewModel(
    private val repository: InventoryRepository
): ViewModel() {
    private val _inventories = MutableLiveData<List<InventoryItem>>()
    val inventories: LiveData<List<InventoryItem>> = _inventories

    private val _errMessage = MutableLiveData<String>()
    val errMessage: LiveData<String> = _errMessage

    fun fetchInventories() {
        repository.getInventoriesData(
            {data ->
            _inventories.postValue(data)
            },
            {
                data ->
                _errMessage.postValue(data)
            }
        )
    }
}