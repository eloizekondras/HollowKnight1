package com.example.hollowknight.data

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val cost: Int,
    val notchCost: Int,
    val location: String,
    val acquisition: String,
    val abilities: List<String>,
    val drops: List<String>,
    val thumbnailImage: String,
    val detailImage: String
)
