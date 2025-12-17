package com.example.silab_app.models

import com.google.gson.annotations.SerializedName

data class ReserveInformation(
    @SerializedName("subject")
    val subject: List<Subject>,

    @SerializedName("session")
    val session: List<Session>,

    @SerializedName("reserves")
    val reserves: List<Reserves>
)
