package com.ranzan.moneymanagerclone.NavBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ranzan.moneymanagerclone.R

class SettingFragment : Fragment(R.layout.fragment_setting) {


    companion object {

        @JvmStatic
        fun newInstance() =
            SettingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}