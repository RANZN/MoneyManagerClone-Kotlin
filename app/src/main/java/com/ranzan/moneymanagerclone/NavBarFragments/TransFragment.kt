package com.ranzan.moneymanagerclone.NavBarFragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ranzan.moneymanagerclone.AddDataActivity
import com.ranzan.moneymanagerclone.DB.DataDAO
import com.ranzan.moneymanagerclone.DB.DataEntity
import com.ranzan.moneymanagerclone.DB.RoomDataBaseModel
import com.ranzan.moneymanagerclone.R
import com.ranzan.moneymanagerclone.RecyclerView.OnItemClicked
import com.ranzan.moneymanagerclone.RecyclerView.RecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_trans.*

class TransFragment : Fragment(R.layout.fragment_trans), OnItemClicked {
    private lateinit var roomDB: RoomDataBaseModel
    private lateinit var dao: DataDAO

    private var list: MutableList<DataEntity> = mutableListOf()
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            TransFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomDB = RoomDataBaseModel.getDataBaseObject(requireContext())
        dao = roomDB.getDao()
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
        dao.getListFromDB().observe(viewLifecycleOwner, Observer {
            list = it
            setRecyclerView()
            setAmounts()
        })
    }

    private fun setAmounts() {
        var tI = 0F
        var tE = 0F
        list.forEach {
            if (it.type == 1) {
                tI += it.amount
            } else if (it.type == 2) {
                tE += it.amount
            }
        }
        totalIncome.text = String.format("₹ %.02f", tI)
        totalExpenses.text = String.format("₹ %.02f", tE)
        totalAmount.text = String.format("₹ %.2f", tI - tE)

    }


    override fun onItemClicked(data: DataEntity?, position: Int) {
        val intent = Intent(context, AddDataActivity::class.java)
        intent.putExtra("pos", data?.id)
//        val bundle = Bundle()
//        bundle.putSerializable("data",data)
//        intent.putExtra("data",data)
        startActivity(intent)
    }

}