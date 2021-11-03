package com.ranzan.moneymanagerclone.DB

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(tableName = "Section Entity")
data class SectionEntity(
    @ColumnInfo(name = "Date")
    val Date: String,
    @ColumnInfo(name = "List")
    val list: MutableList<DataEntity>
) {

}