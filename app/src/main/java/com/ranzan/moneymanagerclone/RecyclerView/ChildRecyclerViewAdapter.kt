package com.ranzan.moneymanagerclone.RecyclerView

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ranzan.moneymanagerclone.DB.DataEntity
import com.ranzan.moneymanagerclone.R

class ChildRecyclerViewAdapter(
    private var list: MutableList<DataEntity>,
//    private val onItemClicked: OnItemClicked
) :
    RecyclerView.Adapter<ChildRecyclerViewAdapter.ChildRecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildRecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.child_item_layout, parent, false)
        return ChildRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holderChild: ChildRecyclerViewHolder, position: Int) {
        holderChild.setData(list[position])
//        holderChild.setData(list[position], onItemClicked)
    }

    override fun getItemCount(): Int = list.size

    class ChildRecyclerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var account: TextView = view.findViewById(R.id.itemAccount)
        private var note: TextView = view.findViewById(R.id.itemNote)
        private var amount: TextView = view.findViewById(R.id.itemAmount)
        private var constraintLayout: ConstraintLayout = view.findViewById(R.id.constrainLayout)

        //        fun setData(data: DataEntity, onItemClicked: OnItemClicked) {
        fun setData(data: DataEntity) {
            if (data.note.isNotBlank())
                note.text = data.note + "\n" + data.category
            else
                note.text = data.category

            account.text = data.account
            amount.text = "₹ " + data.amount.toString()
            amount.run {
                when {
                    data.type === 1 -> {
                        setTextColor(Color.rgb(63, 81, 181))
                    }
                    data.type === 2 -> {
                        setTextColor(Color.rgb(225, 51, 51))
                    }
                    else -> {
                        setTextColor(Color.rgb(71, 71, 71))
                    }
                }

            }
//            constraintLayout.setOnClickListener {
//                onItemClicked.onItemClicked(
//                    data,
//                    adapterPosition
//                )
//            }
        }

    }
}