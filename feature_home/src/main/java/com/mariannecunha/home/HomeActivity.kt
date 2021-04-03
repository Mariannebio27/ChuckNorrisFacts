package com.mariannecunha.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.mariannecunha.home.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {

    private val welcomeFragment by lazy { com.mariannecunha.home.WelcomeFragment.newInstance() }
    private val searchFragment by lazy { com.mariannecunha.search.SearchFragment.newInstance() }
    private var factListFragment: com.mariannecunha.factlist.FactListFragment? = null
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    private fun setUpObservers() = with(binding) {
        welcomeFragment.clickLiveData.observe(
            this@HomeActivity,
            Observer {
                changeFragment(searchFragment)
                menuBottomNavigationView.selectedItemId = R.id.action_search
            }
        )

        searchFragment.clickSearchLiveData.observe(
            this@HomeActivity,
            Observer {
                factListFragment = com.mariannecunha.factlist.FactListFragment.newInstance(it)
                changeFragment(factListFragment)
                menuBottomNavigationView.selectedItemId = R.id.action_home
            }
        )
    }

    private fun setUpBottomMenu() {
        binding.menuBottomNavigationView.setOnNavigationItemSelectedListener {
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
