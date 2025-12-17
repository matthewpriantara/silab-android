package com.example.silab_app.models

data class Subject(
    val subject_id: Int,
    val subject_name: String
) {
    override fun toString(): String {
        return subject_name
    }
}
