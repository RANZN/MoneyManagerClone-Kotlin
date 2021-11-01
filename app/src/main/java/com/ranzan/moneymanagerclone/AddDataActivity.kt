package com.ranzan.moneymanagerclone

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ranzan.moneymanagerclone.DB.DataDAO
import com.ranzan.moneymanagerclone.DB.DataEntity
import com.ranzan.moneymanagerclone.DB.RoomDataBaseModel
import kotlinx.android.synthetic.main.activity_add_data.*
import kotlinx.android.synthetic.main.activity_add_data.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class AddDataActivity : AppCompatActivity() {
    private var typeValue = 2
    private lateinit var roomDB: RoomDataBaseModel
    private lateinit var dao: DataDAO
    private lateinit var getDate: String
    private lateinit var getTime: String
    private var modify = false
    private var iD: Int = 0
    private lateinit var data:DataEntity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)
        roomDB = RoomDataBaseModel.getDataBaseObject(this)
        dao = roomDB.getDao()
        btnRadioColorChange()
        selectedRadioBtn()
        pickDateAndTime()
        selectAccount()
        modifyData()
        if (!modify) addData()
        else updateData()
    }

    // for modifying data setting all details directly
    private fun modifyData() {
        if (intent != null && intent.extras != null && intent.extras!!.containsKey("pos")) {
            iD = intent.getIntExtra("pos", -1)
            modify = true
            btnDelete.visibility = View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                data = dao.getDataFromDB(iD)
                setModifiedData(data)
            }
        }
    }

    private fun setModifiedData(data: DataEntity) {
        data.apply {
            typeValue = type
            getDate = date
            getTime = time
        }
        date.text = "$getDate    $getTime"
        account.text = data.account
        category.setText(data.category)
        amount.setText(data.amount.toString())
        note.setText(data.note)
        description.setText(data.description)
        btnRadioColorChange()
    }

    //for modifying data and update to database
    private fun updateData() {
        btnSave.setOnClickListener {
            if (account.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select account", Toast.LENGTH_SHORT).show()
            } else if (amount.length() > 0) {
                val dataEntity = DataEntity(
                    typeValue,
                    getDate,
                    getTime,
                    account.text.toString(),
                    category.text.toString(),
                    amount.text.toString().toInt(),
                    note.text.toString(),
                    description.text.toString()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    dao.updateData(dataEntity)
                }
                Toast.makeText(this, "Modified Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AddDataActivity, MainActivity::class.java)
                startActivity(intent)
            } else amount.error = "Enter Amount"
        }
        btnContinue.setOnClickListener {
            if (account.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select account", Toast.LENGTH_SHORT).show()
            } else if (amount.length() > 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    dao.updateData(
                        data
                    )
                }
                Toast.makeText(this, "Modified Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AddDataActivity, AddDataActivity::class.java)
                startActivity(intent)
            } else amount.error = "Enter Amount"
        }
        btnDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                dao.deleteData(data)
            }
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@AddDataActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    //on save btn and continue btn clicking
    private fun addData() {
        btnSave.setOnClickListener {
            if (account.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select account", Toast.LENGTH_SHORT).show()
            } else if (amount.length() > 0) {
                val dataEntity = DataEntity(
                    typeValue,
                    getDate,
                    getTime,
                    account.text.toString(),
                    category.text.toString(),
                    amount.text.toString().toInt(),
                    note.text.toString(),
                    description.text.toString()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    dao.addDataToDB(
                        dataEntity
                    )
                }
                val intent = Intent(this@AddDataActivity, MainActivity::class.java)
                startActivity(intent)
            } else amount.error = "Enter Amount"
        }
        btnContinue.setOnClickListener {
            if (account.text.toString().isEmpty()) {
                Toast.makeText(this, "Please select account", Toast.LENGTH_SHORT).show()
            } else if (amount.length() > 0) {
                val dataEntity = DataEntity(
                    typeValue,
                    getDate,
                    getTime,
                    account.text.toString(),
                    category.text.toString(),
                    amount.text.toString().toInt(),
                    note.text.toString(),
                    description.text.toString()
                )
                CoroutineScope(Dispatchers.IO).launch {
                    dao.addDataToDB(dataEntity)

                }
                val intent = Intent(this@AddDataActivity, AddDataActivity::class.java)
                startActivity(intent)
            } else amount.error = "Enter Amount"
        }
    }

    //picking date and time to set at date text view
    private fun pickDateAndTime() {
        val year = Calendar.getInstance()[Calendar.YEAR]
        val month = Calendar.getInstance()[Calendar.MONTH]
        val dayOfMonth = Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        val hh = Calendar.getInstance()[Calendar.HOUR]
        val min = Calendar.getInstance()[Calendar.MINUTE]
        getDate =
            String.format("%02d", dayOfMonth) + "/" + String.format("%02d", month + 1) + "/" + year
        getTime = String.format("%02d", hh) + ":" + String.format(
            "%02d",
            min
        ) //+ ":"+String.format("%02d", sec)
        date.text = "$getDate    $getTime"
        date.setOnClickListener {
            getTimeDialog(hh, min)
            getDateDialog()
        }
    }

    //time picker
    private fun getTimeDialog(hh: Int, min: Int) {
        val timePickerDialog = TimePickerDialog(
            this,
            { view, hourOfDay, minute ->
                getTime = String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute)
                date.text = "$getDate    $getTime"
            }, hh, min, true
        )
        timePickerDialog.show()
    }

    //date picker
    private fun getDateDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                getDate = String.format("%02d", dayOfMonth) + "/" + String.format(
                    "%02d",
                    month + 1
                ) + "/" + year
            },
            Calendar.getInstance()[Calendar.YEAR],
            Calendar.getInstance()[Calendar.MONTH],
            Calendar.getInstance()[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }

    //selecting radio btn i.e, expenses,income,transfer
    private fun selectedRadioBtn() {
        rg.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.rgIncome ->
                    typeValue = 1


                R.id.rgExpenses ->
                    typeValue = 2


                R.id.rgTransfer ->
                    typeValue = 3

            }
            btnRadioColorChange()
        }
    }

    //selecting color of radio button with the save btn
    private fun btnRadioColorChange() {
        when (typeValue) {
            1 -> {
                rgIncome.run {
                    setTextColor(resources.getColor(R.color.income))
                    background = resources.getDrawable(R.drawable.bg2)
                    setTypeface(null, Typeface.BOLD)
                }
                rgExpenses.run {
                    setTextColor(resources.getColor(R.color.black))
                    rgExpenses.background = resources.getDrawable(R.drawable.bg)
                    setTypeface(null, Typeface.NORMAL)
                }
                rgTransfer.run {
                    setTextColor(resources.getColor(R.color.black))
                    background = resources.getDrawable(R.drawable.bg)
                    setTypeface(null, Typeface.NORMAL)
                }
                btnSave.setBackgroundColor(resources.getColor(R.color.income))
            }

            2 -> {
                rgIncome.run {
                    setTypeface(null, Typeface.NORMAL)
                    setTextColor(resources.getColor(R.color.black))
                    background = resources.getDrawable(R.drawable.bg)
                }
                rgExpenses.run {
                    setTextColor(resources.getColor(R.color.expense))
                    background = resources.getDrawable(R.drawable.bg2)
                    setTypeface(null, Typeface.BOLD)
                }
                rgTransfer.run {
                    setTypeface(null, Typeface.NORMAL)
                    setTextColor(resources.getColor(R.color.black))
                    background = resources.getDrawable(R.drawable.bg)
                }
                btnSave.setBackgroundColor(resources.getColor(R.color.expense))
            }


            3 -> {

                rgTransfer.run {
                    setTypeface(null, Typeface.BOLD)
                    setTextColor(resources.getColor(R.color.transfer))
                    background = resources.getDrawable(R.drawable.bg2)
                }
                rgExpenses.run {
                    setTextColor(resources.getColor(R.color.black))
                    setTypeface(null, Typeface.NORMAL)
                    background = resources.getDrawable(R.drawable.bg)
                }
                rgIncome.run {
                    setTypeface(null, Typeface.NORMAL)
                    setTextColor(resources.getColor(R.color.black))
                    background = resources.getDrawable(R.drawable.bg)
                }
                btnSave.setBackgroundColor(resources.getColor(R.color.transfer))
            }
        }
    }

    //selection of account type using dialog box
    private fun selectAccount() {
        account.setOnClickListener {
            val builder = AlertDialog.Builder(this@AddDataActivity)
            builder.setTitle("Account")
            val accountList = arrayOf("Cash", "Account", "Card")
            builder.setItems(
                accountList
            ) { dialog, which ->
                when (which) {
                    0 -> account.text = "Cash"
                    1 -> account.text = "Account"
                    2 -> account.text = "Card"
                }
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    //on back pressing always move to main activity
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@AddDataActivity, MainActivity::class.java)
        startActivity(intent)
    }
}
