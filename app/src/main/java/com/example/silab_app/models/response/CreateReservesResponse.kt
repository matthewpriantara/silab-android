package com.example.silab_app.models.response

import com.google.gson.annotations.SerializedName


data class CreateReservesResponse (
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: CountResponse
)
data class CountResponse(
    @SerializedName("count")
    val count: Int
)