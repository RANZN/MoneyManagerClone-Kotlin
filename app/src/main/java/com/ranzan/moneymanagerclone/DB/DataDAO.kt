package com.ranzan.moneymanagerclone.DB

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DataDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDataToDB(dataEntity: DataEntity)

    @Update
    fun updateData(dataEntity: DataEntity)

    @Delete
    fun deleteData(dataEntity: DataEntity)

    @Query("Select * from `table`")
    fun getListFromDB(): LiveData<MutableList<DataEntity>>

    @Query("select * from `table` where id=:id")
    fun getDataFromDB(id: Int): DataEntity

    @Query("select sum(amount) from `table` where type==1")
    fun getTotalIncome(): LiveData<Float>

    @Query("select sum(amount) from `table` where type==2")
    fun getTotalExpenses(): LiveData<Float>
}