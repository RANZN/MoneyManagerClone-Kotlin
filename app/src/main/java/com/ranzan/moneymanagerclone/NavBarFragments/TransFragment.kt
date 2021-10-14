package com.ranzan.moneymanagerclone.NavBarFragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranzan.moneymanagerclone.AddDataActivity
import com.ranzan.moneymanagerclone.Database.DatabaseHandler
import com.ranzan.moneymanagerclone.Database.DatabaseModel
import com.ranzan.moneymanagerclone.R
import com.ranzan.moneymanagerclone.RecyclerView.OnItemClicked
import com.ranzan.moneymanagerclone.RecyclerView.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_trans.*

class TransFragment : Fragment(R.layout.fragment_trans), OnItemClicked {
    private lateinit var dbHandler: DatabaseHandler
    private var list: MutableList<DatabaseModel> = mutableListOf()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
//    private var totalIncome = 0
//    private var totalExpenses = 0

    companion object {
        @JvmStatic
        fun newInstance() =
            TransFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHandler = DatabaseHandler(context)
    }

    private fun setRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(list, this)
        recyclerView.apply {
            adapter = recyclerViewAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = dbHandler.getDataList()
        list.reverse()
        setRecyclerView()
        totalIncome.text="₹ ${dbHandler.getTotalIncome().toString()}"
        totalExpenses.text="₹ "+dbHandler.getTotalExpenses().toString()
        totalAmount.text="₹ "+(dbHandler.getTotalIncome()-dbHandler.getTotalExpenses()).toString()
    }

    override fun onItemClicked(data: DatabaseModel?, position: Int) {
        val intent = Intent(context, AddDataActivity::class.java)
        intent.putExtra("pos", data?.id)
        startActivity(intent)
    }

}