package com.ranzan.moneymanagerclone.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DataEntity::class, SectionEntity::class], version = 1)
abstract class RoomDataBaseModel : RoomDatabase() {
    abstract fun getDao(): DataDAO

    companion object {
        private var INSTANCE: RoomDataBaseModel? = null

        fun getDataBaseObject(context: Context): RoomDataBaseModel {
            if (INSTANCE == null) {
                val builder = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDataBaseModel::class.java,
                    "table"
                )

                builder.fallbackToDestructiveMigration()
                INSTANCE = builder.build()

                return INSTANCE!!
            } else
                return INSTANCE!!
        }
    }
}