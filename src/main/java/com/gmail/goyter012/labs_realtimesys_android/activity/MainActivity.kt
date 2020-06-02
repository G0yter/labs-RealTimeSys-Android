package com.gmail.goyter012.labs_realtimesys_android.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.gmail.goyter012.labs_realtimesys_android.R
import com.gmail.goyter012.labs_realtimesys_android.fragment.FirstFragment
import com.gmail.goyter012.labs_realtimesys_android.fragment.SecondFragment
import com.gmail.goyter012.labs_realtimesys_android.fragment.ThirdFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = supportActionBar!!
        setupFirst()

        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_bar)

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.first_lab -> {
                    setupFirst()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.second_lab -> {
                    setupSecond()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.third_lab -> {
                    setupThird()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }


    }


    private fun setupFirst() {
        toolbar.title = applicationContext.getString(R.string.lab1_title)
        val firstFragment = FirstFragment.newInstance()
        openFragment(firstFragment)
    }

    private fun setupSecond() {
        toolbar.title = applicationContext.getString(R.string.lab2_title)
        val secondFragment= SecondFragment.newInstance()
        openFragment(secondFragment)

    }

    private fun setupThird() {
        toolbar.title = applicationContext.getString(R.string.lab3_title)
        val thirdFragment = ThirdFragment.newInstance()
        openFragment(thirdFragment)
    }


    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commitAllowingStateLoss()
    }


}
