package com.ranzan.moneymanagerclone.RecyclerView

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ranzan.moneymanagerclone.Database.DatabaseModel
import com.ranzan.moneymanagerclone.R

class RecyclerViewAdapter(
    private var list: MutableList<DatabaseModel>,
    private val onItemClicked: OnItemClicked
) :
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.setData(list[position], onItemClicked)
    }

    override fun getItemCount(): Int = list.size


    class RecyclerViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private var account: TextView = view.findViewById(R.id.itemAccount)
        private var category: TextView = view.findViewById(R.id.itemCategory)
        private var note: TextView = view.findViewById(R.id.itemNote)
        private var amount: TextView = view.findViewById(R.id.itemAmount)
        private var date_n_time: TextView = view.findViewById(R.id.dateNtime)
        private var constraintLayout: ConstraintLayout = view.findViewById(R.id.constrainLayout)

        fun setData(data: DatabaseModel, onItemClicked: OnItemClicked) {
            category.text = data.category
            note.text = data.note
            account.text = data.account
            amount.text = data.amount.toString()
            date_n_time.text = data.date + "    " + data.time
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
            constraintLayout.setOnClickListener {
                onItemClicked.onItemClicked(
                    data,
                    adapterPosition
                )
            }
        }

    }
}