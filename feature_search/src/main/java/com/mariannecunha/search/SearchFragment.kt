package com.mariannecunha.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavAction
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariannecunha.core.livedata.SingleEventLiveData
import com.mariannecunha.search.databinding.FragmentSearchBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : Fragment() {

    private val viewModel by sharedViewModel<SearchViewModel>()
    private val tagCloudAdapter by inject<SearchListAdapter> {
        parametersOf ({ searchText: String -> onSearchClick(searchText)})
    }
    private val searchedWordAdapter by inject<SearchedWordsListAdapter>() {
        parametersOf({ searchText: String -> onSearchClick(searchText)})
    }
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBinding(view)
        setUpRecyclerViews()
        setUpShimmer()
        viewModel.getCategories()
        setUpClickSearchFragment()
        setUpObserver()
    }

    override fun onResume() {
        super.onResume()

        val words = viewModel.getWordList()
        searchedWordAdapter.updateWords(words)
    }

    override fun onPause() {
        super.onPause()

        cleanSearch()
    }

    private fun setUpBinding(view: View) {
        binding = FragmentSearchBinding.bind(view)
    }

    private fun cleanSearch() {
        binding.searchTextInputEditText.text?.clear()
    }

    private fun setUpShimmer() = with(binding.shimmerViewContainer){
        visibility = View.VISIBLE
        startShimmer()
    }

    private fun setUpClickSearchFragment() = with(binding) {
        searchButton.setOnClickListener {
            val searchSentence = searchTextInputEditText.text.toString()

            if (searchSentence.isNotEmpty()) {
                onSearchClick(searchSentence)
            }
        }
    }

    private fun setUpRecyclerViews() = with(binding) {
        val gridLayoutManager = GridLayoutManager(context, 3)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        tagCloudRecyclerView.setHasFixedSize(true)
        tagCloudRecyclerView.layoutManager = gridLayoutManager
        tagCloudRecyclerView.adapter = tagCloudAdapter

        recentSearchedRecyclerView.setHasFixedSize(true)
        recentSearchedRecyclerView.layoutManager = linearLayoutManager
        recentSearchedRecyclerView.adapter = searchedWordAdapter
    }

    private fun setUpObserver() = with(binding) {
        viewModel.categoriesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                shimmerViewContainer.visibility = View.GONE
                shimmerViewContainer.stopShimmer()
                tagCloudRecyclerView.visibility = View.VISIBLE
                tagCloudAdapter.updateCategories(it)
            }
        )
    }

    private fun onSearchClick(searchText: String) {
        viewModel.onSearchClick(searchText)
    }
}
