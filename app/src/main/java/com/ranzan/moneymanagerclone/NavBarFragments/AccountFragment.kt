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
import kotlinx.android.synthetic.main.fragment_account.*
import org.eazegraph.lib.models.PieModel

class AccountFragment : Fragment(R.layout.fragment_account) {
    private lateinit var dao: DataDAO
    private lateinit var list: MutableList<DataEntity>
    private val currency = "₹"

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dao = RoomDataBaseModel.getDataBaseObject(requireContext()).getDao()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dao.getListFromDB().observe(viewLifecycleOwner, Observer {
            list = it
            setData()
        })
    }

    private fun setData() {
        var accountSum = 0f
        var cardSum = 0f
        var cashSum = 0f
        var incomeSum = 0f
        var expensesSum = 0f
        list.forEach {
            when (it.account) {
                "Cash" -> cashSum += it.amount
                "Account" -> accountSum += it.amount
                "Card" -> cardSum += it.amount
            }
            when (it.type) {
                1 -> incomeSum += it.amount
                2 -> expensesSum += it.amount
            }
        }
        account.text = String.format("$currency %.2f", accountSum)
        card.text = String.format("$currency %.2f", cardSum)
        cash.text = String.format("$currency %.2f", cashSum)
        assetsValue.text = String.format("$currency %.2f", incomeSum)
        liaValue.text = String.format("$currency %.2f", expensesSum)
        totalValue.text = String.format("$currency %.2f", incomeSum - expensesSum)

        accountPieChart.addPieSlice(
            PieModel(
                "Income",
                incomeSum,
                Color.parseColor("#3F51B5")
            )
        )
        accountPieChart.addPieSlice(
            PieModel(
                "Expenses",
                expensesSum,
                Color.parseColor("#E13A3A")
            )
        )
        accountPieChart.addPieSlice(
            PieModel(
                "Transfer",
                Math.abs(incomeSum - expensesSum),
                Color.parseColor("#474747")
            )
        )
        accountPieChart.startAnimation()
    }


}