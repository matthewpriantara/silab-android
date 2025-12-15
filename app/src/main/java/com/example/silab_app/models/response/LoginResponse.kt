package com.example.silab_app.models.response

import com.example.silab_app.models.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("data")
    val data: User
)
