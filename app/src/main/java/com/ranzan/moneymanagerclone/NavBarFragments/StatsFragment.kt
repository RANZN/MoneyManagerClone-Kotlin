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
import org.eazegraph.lib.charts.PieChart
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
        var totalCash = 0.0
        var totalCard = 0.0
        var totalAccount = 0.0
        list.forEach {
            when (it.account) {
                "Cash" -> {
                    totalCash += it.amount
                }
                "Card" -> {
                    totalCard += it.amount
                }
                "Account" -> {
                    totalAccount += it.amount
                }
            }
        }
        tvCash.text = String.format("₹ %.2f",totalCash)
        tvCard.text = String.format("₹ %.2f",totalCard)
        tvAccount.text = String.format("₹ %.2f",totalAccount)
        perCard.text = String.format(
            "%.2f",
            totalCard / (totalAccount + totalCash + totalCard) * 100
        )
        perAccount.text = String.format(
            "%.2f",
            totalAccount / (totalAccount + totalCash + totalCard) * 100
        )
        perCash.text = String.format(
            "%.2f",
            totalCash / (totalAccount + totalCash + totalCard) * 100
        )

        pieChart.addPieSlice(
            PieModel(
                "Cash",
                totalCash.toFloat(),
                Color.parseColor("#474747")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Card",
                totalCard.toFloat(),
                Color.parseColor("#3F51B5")
            )
        )
        pieChart.addPieSlice(
            PieModel(
                "Account",
                totalAccount.toFloat(),
                Color.parseColor("#E13A3A")
            )
        )
        pieChart.startAnimation()

    }
}