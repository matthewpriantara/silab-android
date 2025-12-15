package com.example.silab_app.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CreateReserves(
    @SerializedName("inventories_id")
    val inventories_id: Int,

    @SerializedName("pic")
    val pic: String,

    @SerializedName("subject_id")
    val subject_id: Int,

    @SerializedName("tanggal")
    val tanggal: Date,

    @SerializedName("session_id")
    val session_id: Int
)