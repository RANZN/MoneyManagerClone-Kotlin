package com.ranzan.moneymanagerclone.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHandler(private val context: Context?) : SQLiteOpenHelper(context, "db", null, 1) {
    companion object {
        const val TABLE_NAME = "DATA"
        const val ID = "Id"
        const val TYPE = "Type"
        const val DATE = "Date"
        const val TIME = "Time"
        const val ACCOUNT = "Account"
        const val CATEGORY = "category"
        const val AMOUNT = "Amount"
        const val NOTE = "Note"
        const val DESC = "Description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery =
            "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY, $TYPE Int,$DATE text," +
                    "$TIME text,$ACCOUNT text,$CATEGORY text,$AMOUNT text,$NOTE text,$DESC text)"
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun insertData(
        type: Int,
        date: String,
        time: String,
        account: String,
        category: String,
        amount: Int,
        note: String,
        desc: String
    ) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(TYPE, type)
        values.put(DATE, date)
        values.put(TIME, time)
        values.put(ACCOUNT, account)
        values.put(CATEGORY, category)
        values.put(AMOUNT, amount)
        values.put(NOTE, note)
        values.put(DESC, desc)
        db.insert(TABLE_NAME, null, values)
    }

    fun updateData(
        id: Int,
        type: Int,
        date: String,
        time: String,
        account: String,
        category: String,
        amount: Int,
        note: String,
        desc: String
    ) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(ID, id)
        values.put(TYPE, type)
        values.put(DATE, date)
        values.put(TIME, time)
        values.put(ACCOUNT, account)
        values.put(CATEGORY, category)
        values.put(AMOUNT, amount)
        values.put(NOTE, note)
        values.put(DESC, desc)
        db.update(TABLE_NAME, values, "id= $id", null)
    }

    fun deleteData(id: Int) {
        val db = writableDatabase
        val affectedRows = db.delete(TABLE_NAME, "id=$id", null)
        if (affectedRows > 0)
            Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Cannot delete", Toast.LENGTH_SHORT).show()
    }

    fun getData(id: Int): DatabaseModel {
        var databaseModel: DatabaseModel? = null
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE id=$id"
        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            //fetching column data
            val idIndex = cursor.getColumnIndex(ID)
            val typeIndex = cursor.getColumnIndex(TYPE)
            val dateIndex = cursor.getColumnIndex(DATE)
            val timeIndex = cursor.getColumnIndex(TIME)
            val accountIndex = cursor.getColumnIndex(ACCOUNT)
            val categoryIndex = cursor.getColumnIndex(CATEGORY)
            val amountIndex = cursor.getColumnIndex(AMOUNT)
            val noteIndex = cursor.getColumnIndex(NOTE)
            val descIndex = cursor.getColumnIndex(DESC)

            val id = cursor.getInt(idIndex)
            val type = cursor.getInt(typeIndex)
            val date = cursor.getString(dateIndex)
            val time = cursor.getString(timeIndex)
            val account = cursor.getString(accountIndex)
            val category = cursor.getString(categoryIndex)
            val amount = cursor.getInt(amountIndex)
            val note = cursor.getString(noteIndex)
            val desc = cursor.getString(descIndex)
            databaseModel =
                DatabaseModel(id, type, date, time, account, category, amount, note, desc)
        }
        return databaseModel!!
    }

    fun getDataList(): MutableList<DatabaseModel> {
        val listRoutines = mutableListOf<DatabaseModel>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()

            //fetching column data
            val idIndex = cursor.getColumnIndex(ID)
            val typeIndex = cursor.getColumnIndex(TYPE)
            val dateIndex = cursor.getColumnIndex(DATE)
            val timeIndex = cursor.getColumnIndex(TIME)
            val accountIndex = cursor.getColumnIndex(ACCOUNT)
            val categoryIndex = cursor.getColumnIndex(CATEGORY)
            val amountIndex = cursor.getColumnIndex(AMOUNT)
            val noteIndex = cursor.getColumnIndex(NOTE)
            val descIndex = cursor.getColumnIndex(DESC)

            do {
                //fetching row data
                val id = cursor.getInt(idIndex)
                val type = cursor.getInt(typeIndex)
                val date = cursor.getString(dateIndex)
                val time = cursor.getString(timeIndex)
                val account = cursor.getString(accountIndex)
                val category = cursor.getString(categoryIndex)
                val amount = cursor.getInt(amountIndex)
                val note = cursor.getString(noteIndex)
                val desc = cursor.getString(descIndex)
                val databaseModel =
                    DatabaseModel(id, type, date, time, account, category, amount, note, desc)
                listRoutines.add(databaseModel)

            } while (cursor.moveToNext())
            cursor.close()
        }
        return listRoutines
    }

    fun getTotalIncome(): Int {
        var total = 0
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE Type=1"
        val cursor = db.rawQuery(query, null)

        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            val amountIndex = cursor.getColumnIndex(AMOUNT)
            do {
                total += cursor.getInt(amountIndex)
            } while (cursor.moveToNext())
        }
        return total
    }

    fun getTotalExpenses(): Int {
        var total = 0
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE Type=2"
        val cursor = db.rawQuery(query, null)

        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            val amountIndex = cursor.getColumnIndex(AMOUNT)
            do {
                total += cursor.getInt(amountIndex)
            } while (cursor.moveToNext())
        }
        return total
    }
}