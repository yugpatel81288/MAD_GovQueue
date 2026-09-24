package com.example.madgovqueue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Open Home by default
        if (savedInstanceState == null) {
            openFragment(HomeFragment())
            bottomNavigation.selectedItemId = R.id.nav_home
        }

        // Bottom Navigation
        bottomNavigation.setOnItemSelectedListener { item ->

            when (item.itemId) {

                R.id.nav_home -> {
                    openFragment(HomeFragment())
                    true
                }

                R.id.nav_offices -> {
                    openFragment(OfficesFragment())
                    true
                }

                R.id.nav_reports -> {
                    openFragment(ReportsFragment())
                    true
                }

                R.id.nav_profile -> {
                    openFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.navHostFragment, fragment)
            .commit()
    }

    fun switchToOfficesTab() {
        bottomNavigation.selectedItemId = R.id.nav_offices
    }

    fun switchToReportsTab() {
        bottomNavigation.selectedItemId = R.id.nav_reports
    }
}