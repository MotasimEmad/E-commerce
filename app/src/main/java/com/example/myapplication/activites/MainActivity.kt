package com.example.myapplication.activites

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.myapplication.R
import com.example.myapplication.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mActionBar: ActionBar = supportActionBar!!
        mActionBar.hide()

        MoewMenu()
    }

    private fun MoewMenu() {
        setFragment(HomeFragment.newIntance())
        BottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.ic_profile))
        BottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.ic_home))
        BottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.ic_category))

        BottomNavigation.setOnClickMenuListener {
            when(it.id) {
                1 -> setFragment(ProfileFragment.newIntance())
                2 -> setFragment(HomeFragment.newIntance())
                3 -> setFragment(CategoryFragment.newIntance())

                else -> " "
            }
        }

        BottomNavigation.show(2)
    }

    private fun setFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.FrameLayout, fragment, "main")
            .commit()
    }
}
