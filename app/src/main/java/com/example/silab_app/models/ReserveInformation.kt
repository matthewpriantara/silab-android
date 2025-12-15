package com.example.silab_app.models

import com.google.gson.annotations.SerializedName

data class ReserveInformation(
    @SerializedName("subjects")
    val subject: List<Subject>,

    @SerializedName("reserves")
    val reserves: List<Reserves>
)
