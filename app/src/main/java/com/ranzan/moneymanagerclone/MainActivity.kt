package com.ranzan.moneymanagerclone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ranzan.moneymanagerclone.DB.DataDAO
import com.ranzan.moneymanagerclone.DB.RoomDataBaseModel
import com.ranzan.moneymanagerclone.NavBarFragments.AccountFragment
import com.ranzan.moneymanagerclone.NavBarFragments.SettingFragment
import com.ranzan.moneymanagerclone.NavBarFragments.StatsFragment
import com.ranzan.moneymanagerclone.NavBarFragments.TransFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectFragment(TransFragment.newInstance())
        bottomNavBar()
        addData()
    }

    private fun addData() {
        addDataBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, AddDataActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bottomNavBar() {
        bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.trans -> selectFragment(TransFragment.newInstance())
                R.id.stats -> selectFragment(StatsFragment.newInstance())
                R.id.account -> selectFragment(AccountFragment.newInstance())
                R.id.setting -> selectFragment(SettingFragment.newInstance())
            }
            true
        }
    }

    private fun selectFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment).commit()
}
