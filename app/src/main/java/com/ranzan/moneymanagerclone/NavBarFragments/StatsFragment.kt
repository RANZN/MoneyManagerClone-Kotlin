package com.ranzan.moneymanagerclone.NavBarFragments

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ranzan.moneymanagerclone.DB.DataDAO
import com.ranzan.moneymanagerclone.DB.DataEntity
import com.ranzan.moneymanagerclone.DB.RoomDataBaseModel
import com.ranzan.moneymanagerclone.R
import kotlinx.android.synthetic.main.fragment_stats.*
import org.eazegraph.lib.models.PieModel


class StatsFragment : Fragment(R.layout.fragment_stats) {
    private lateinit var roomDB: RoomDataBaseModel
    private lateinit var dao: DataDAO
    private lateinit var list: MutableList<DataEntity>

    companion object {
        @JvmStatic
        fun newInstance() =
            StatsFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        roomDB = RoomDataBaseModel.getDataBaseObject(requireContext())
        dao = roomDB.getDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao.getListFromDB().observe(viewLifecycleOwner, Observer {
            list = it
            setData()
        })
    }

    private fun setData() {
        var income = 0f
        var expenses = 0f
        var transfer = 0f
        list.forEach {
            when (it.type) {
                1 -> {
                    income += it.amount
                }
                2 -> {
                    expenses += it.amount
                }
                3 -> {
                    transfer += it.amount
                }
            }
        }
        tvCash.text = String.format("₹ %.2f",income)
        tvCard.text = String.format("₹ %.2f",expenses)
        tvAccount.text = String.format("₹ %.2f",transfer)
        perExpense.text = String.format(
            "%.2f %%",
            expenses / (transfer + income + expenses) * 100
        )
        perTransfer.text = String.format(
            "%.2f %%",
            transfer / (transfer + income + expenses) * 100
        )
        perIncome.text = String.format(
            "%.2f %%",
            income / (transfer + income + expenses) * 100
        )

        statusPieChart.addPieSlice(
            PieModel(
                "Income",
                income,
                Color.parseColor("#3F51B5")
            )
        )
        statusPieChart.addPieSlice(
            PieModel(
                "Expenses",
                expenses,
                Color.parseColor("#E13A3A")
            )
        )
        statusPieChart.addPieSlice(
            PieModel(
                "Transfer",
                transfer,
                Color.parseColor("#474747")
            )
        )
        statusPieChart.startAnimation()

    }
}