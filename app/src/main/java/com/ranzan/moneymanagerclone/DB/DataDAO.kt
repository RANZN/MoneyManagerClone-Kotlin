package com.ranzan.moneymanagerclone.DB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDataToDB(dataEntity: DataEntity)

    @Delete
    fun deleteData(dataEntity: DataEntity)

    @Query("Select * from `table`")
    fun getListFromDB(): LiveData<MutableList<DataEntity>>

    @Query("select * from `table` where id=:id")
    fun getDataFromDB(id: Int): DataEntity

    @Update
    fun updateData(dataEntity: DataEntity)

    @Query("select sum(type) from `table` where type==1" )
    fun getTotalIncome(): Int

    @Query("select sum(type) from `table` where type==2")
    fun getTotalExpenses(): Int
}