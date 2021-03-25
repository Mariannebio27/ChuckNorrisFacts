package com.mariannecunha.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mariannecunha.presentation.factlist.FactListFragment
import com.mariannecunha.presentation.factlist.WelcomeFragment
import com.mariannecunha.presentation.search.SearchFragment

class HomeActivity : AppCompatActivity() {

    private val welcomeFragment by lazy { WelcomeFragment.newInstance() }
    private val searchFragment by lazy { SearchFragment.newInstance() }
    private lateinit var bottomMenu: BottomNavigationView
    private var factListFragment: FactListFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fact_list)

        changeFragment(welcomeFragment)
        setUpObservers()
        setUpBottomMenu()
    }

    private fun changeFragment(fragment: Fragment?) {
        fragment?.let {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, it)
                .commit()
        }
    }

    private fun setUpObservers() {
        welcomeFragment.clickLiveData.observe(
            this,
            Observer {
                changeFragment(searchFragment)
                bottomMenu.selectedItemId = R.id.action_search
            }
        )

        searchFragment.clickSearchLiveData.observe(
            this,
            Observer {
                factListFragment = FactListFragment.newInstance(it)
                changeFragment(factListFragment)
                bottomMenu.selectedItemId = R.id.action_home
            }
        )
    }

    private fun setUpBottomMenu() {

        bottomMenu = findViewById(R.id.menu_bottom_navigation_view)

        bottomMenu.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    if (factListFragment == null) {
                        changeFragment(welcomeFragment)
                    } else {
                        changeFragment(factListFragment)
                    }
                }
                R.id.action_search -> {
                    changeFragment(searchFragment)
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
