package com.mariannecunha.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.mariannecunha.home.databinding.ActivityHomeBinding
import com.mariannecunha.search.SearchFragment
import com.mariannecunha.search.SearchFragmentDirections
import com.mariannecunha.search.SearchViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()
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
        searchViewModel.clickSearchLiveData.observe(this@HomeActivity, Observer { searchText ->
            navController.navigate(SearchFragmentDirections.actionSearchFragmentToFactListFragment(searchText))
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
