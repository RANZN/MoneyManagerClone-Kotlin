package com.ranzan.moneymanagerclone.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ranzan.moneymanagerclone.DB.SectionEntity
import com.ranzan.moneymanagerclone.R

class MainRecyclerViewAdapter(private val list: MutableList<SectionEntity>) :
    RecyclerView.Adapter<MainRecyclerViewAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_item_layout, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
        holder.setData(list[position])


    override fun getItemCount(): Int = list.size

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val date: TextView = view.findViewById<TextView>(R.id.dateNTime)
        private val childRecyclerView: RecyclerView =
            view.findViewById<RecyclerView>(R.id.childRecyclerView)

        fun setData(data: SectionEntity) {
            date.text = data.Date
            val childRecyclerViewAdapter = ChildRecyclerViewAdapter(data.list)
            childRecyclerView.adapter = childRecyclerViewAdapter
        }

    }

}