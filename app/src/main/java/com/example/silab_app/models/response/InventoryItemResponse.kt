package com.example.silab_app.models.response

import com.example.silab_app.models.InventoryItem
import com.google.gson.annotations.SerializedName

data class InventoryItemResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<InventoryItem>
)
