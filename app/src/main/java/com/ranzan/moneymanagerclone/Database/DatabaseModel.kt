package com.ranzan.moneymanagerclone.Database

data class DatabaseModel(
    val id: Int,
    val type: Int,
    val date: String,
    val time: String,
    val account: String,
    val category: String,
    val amount: Int,
    val note: String,
    val description: String
)
