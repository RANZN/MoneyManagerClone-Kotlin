package com.ranzan.moneymanagerclone.NavBarFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ranzan.moneymanagerclone.R

class AccountFragment : Fragment(R.layout.fragment_account) {


    companion object {

        @JvmStatic
        fun newInstance() =
            AccountFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}