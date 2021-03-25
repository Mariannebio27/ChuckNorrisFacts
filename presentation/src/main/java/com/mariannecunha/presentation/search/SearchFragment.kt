package com.mariannecunha.presentation.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mariannecunha.core.livedata.SingleEventLiveData
import com.mariannecunha.presentation.R
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : Fragment() {

    private val viewModel by viewModel<SearchViewModel>()
    private val _clickSearchLiveData =
        SingleEventLiveData<String>()
    val clickSearchLiveData: LiveData<String> = _clickSearchLiveData
    private val tagCloudAdapter by inject<SearchListAdapter> {
        parametersOf(_clickSearchLiveData)
    }
    private val searchedWordAdapter by inject<SearchedWordsListAdapter>() {
        parametersOf(_clickSearchLiveData)
    }
    private lateinit var tagCloudRecyclerView: RecyclerView
    private lateinit var searchedWordRecyclerView: RecyclerView
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var searchInputText: TextInputEditText
    private lateinit var searchButton: MaterialButton
    private lateinit var shimmerFrameLayout: ShimmerFrameLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpInputText(view)
        setUpRecyclerView(view)
        setUpShimmer(view)
        viewModel.getCategories()
        setUpClickSearchFragment(view)
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

    private fun setUpInputText(view: View) {
        searchInputText = view.findViewById(R.id.search_text_input_edit_text)
    }

    private fun cleanSearch() {
        searchInputText.text?.clear()
    }

    private fun setUpShimmer(view: View) {
        shimmerFrameLayout = view.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container)

        shimmerFrameLayout.visibility = View.VISIBLE
        shimmerFrameLayout.startShimmer()
    }

    private fun setUpClickSearchFragment(view: View) {
        searchButton = view.findViewById(R.id.search_button)
        searchButton.setOnClickListener {
            val searchSentence = searchInputText.text.toString()

            if (searchSentence.isNotEmpty()) {
                _clickSearchLiveData.postValue(searchSentence)
            }
        }
    }

    private fun setUpRecyclerView(view: View) {
        tagCloudRecyclerView = view.findViewById(R.id.tag_cloud_recycler_view)
        gridLayoutManager = GridLayoutManager(context, 3)

        searchedWordRecyclerView = view.findViewById(R.id.recent_searched_recycler_view)
        linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        tagCloudRecyclerView.setHasFixedSize(true)
        tagCloudRecyclerView.layoutManager = gridLayoutManager
        tagCloudRecyclerView.adapter = tagCloudAdapter

        searchedWordRecyclerView.setHasFixedSize(true)
        searchedWordRecyclerView.layoutManager = linearLayoutManager
        searchedWordRecyclerView.adapter = searchedWordAdapter
    }

    private fun setUpObserver() {
        viewModel.categoriesLiveData.observe(
            viewLifecycleOwner,
            Observer {
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmer()
                tagCloudRecyclerView.visibility = View.VISIBLE
                tagCloudAdapter.updateCategories(it)
            }
        )
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
