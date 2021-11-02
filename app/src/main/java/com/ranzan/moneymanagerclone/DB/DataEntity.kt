package com.ranzan.moneymanagerclone.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "TABLE")
data class DataEntity(
    @ColumnInfo(name = "Type")
    val type: Int,
    @ColumnInfo(name = "Date")
    val date: String,
    @ColumnInfo(name = "Time")
    val time: String,
    @ColumnInfo(name = "Account")
    val account: String,
    @ColumnInfo(name = "Category")
    val category: String,
    @ColumnInfo(name = "Amount")
    val amount: Float,
    @ColumnInfo(name = "Note")
    val note: String,
    @ColumnInfo(name = "Description")
    val description: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null
}