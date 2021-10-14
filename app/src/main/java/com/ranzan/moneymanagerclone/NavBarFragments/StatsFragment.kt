package com.ranzan.moneymanagerclone.NavBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ranzan.moneymanagerclone.R


class StatsFragment : Fragment(R.layout.fragment_stats) {


    companion object {

        @JvmStatic
        fun newInstance() =
            StatsFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}