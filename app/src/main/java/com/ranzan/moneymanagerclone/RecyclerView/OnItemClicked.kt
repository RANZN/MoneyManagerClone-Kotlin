package com.ranzan.moneymanagerclone.RecyclerView

import com.ranzan.moneymanagerclone.DB.DataEntity

interface OnItemClicked {
    fun onItemClicked(data: DataEntity?, position: Int)
}