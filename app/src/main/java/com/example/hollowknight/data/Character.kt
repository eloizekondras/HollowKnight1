package com.example.hollowknight.data

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val cost: Int,
    val notchCost: Int?,
    val location: String?,
    val acquisition: String?,
    val image: String?
)


