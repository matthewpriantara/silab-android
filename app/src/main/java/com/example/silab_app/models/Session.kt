package com.example.silab_app.models

data class Session(
    val session_id: Int,
    val start_time: String
) {
    override fun toString(): String {
        return start_time
    }
}