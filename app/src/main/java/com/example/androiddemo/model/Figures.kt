package com.example.androiddemo.model

import java.util.UUID

data class Figures(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val title: String,
    val info: Info
) {
    data class Info(
        val born: String,
        val died: String,
        val occupation: String,
        val notableWork: String
    )
}



