package com.example.silab_app.models.request

import com.example.silab_app.models.CreateReserves
import com.google.gson.annotations.SerializedName

data class CreateReservesRequest(
    @SerializedName("data")
    val data: List<CreateReserves>
)
