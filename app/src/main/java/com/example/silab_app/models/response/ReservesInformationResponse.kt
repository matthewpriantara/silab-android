package com.example.silab_app.models.response

import com.example.silab_app.models.ReserveInformation
import com.google.gson.annotations.SerializedName

data class ReservesInformationResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: ReserveInformation
)
