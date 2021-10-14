package com.ranzan.moneymanagerclone.RecyclerView

import com.ranzan.moneymanagerclone.Database.DatabaseModel

interface OnItemClicked {
    fun onItemClicked(data: DatabaseModel?, position: Int)
}