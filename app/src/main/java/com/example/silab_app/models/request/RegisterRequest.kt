package com.example.silab_app.models.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @SerializedName("username")
    val username: String,

    @SerializedName("nim")
    val nim: String,

    @SerializedName("prodi")
    val prodi: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("password")
    val password: String
)
