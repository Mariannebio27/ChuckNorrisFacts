package com.mariannecunha.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.mariannecunha.home.databinding.ActivityHomeBinding
import com.mariannecunha.search.SearchFragmentDirections
import com.mariannecunha.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()
    private val welcomeViewModel: WelcomeViewModel by viewModel()
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpNavigation()
        setUpBottomMenu()
        setUpObservers()
    }

    private fun setUpObservers() {
        searchViewModel.clickSearchLiveData.observe(this@HomeActivity, Observer {
            navController.navigate(SearchFragmentDirections.actionSearchFragmentToFactListFragment())
        })
        welcomeViewModel.clickLiveData.observe(this@HomeActivity, Observer {
            navController.navigate(WelcomeFragmentDirections.actionWelcomeFragmentToSearchFragment())
        })
    }

    private fun setUpNavigation() {
        supportFragmentManager.findFragmentById(binding.fragmentContainer.id)?.let {
            navController = it.findNavController()
            val graph = navController.navInflater.inflate(R.navigation.home_navigation_graph)
            navController.graph = graph
        }
    }

    private fun setUpBottomMenu() {
        NavigationUI.setupWithNavController(
            binding.menuBottomNavigationView,
            navController
        )
    }
}
