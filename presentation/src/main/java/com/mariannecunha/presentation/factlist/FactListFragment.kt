package com.mariannecunha.presentation.factlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.mariannecunha.core.extensions.hide
import com.mariannecunha.core.extensions.model.SnackbarProperties
import com.mariannecunha.core.extensions.show
import com.mariannecunha.core.extensions.showSnackbar
import com.mariannecunha.domain.model.Fact
import com.mariannecunha.presentation.R
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FactListFragment : Fragment() {

    private val viewModel by viewModel<FactListViewModel>()
    private val adapter by inject<FactListAdapter>()
    private var searchSentence: String? = null
    private lateinit var factListRecyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private val shimmerFrameLayout by lazy { view?.findViewById<ShimmerFrameLayout>(R.id.shimmer_view_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            searchSentence = it.getString(SEARCH_SENTENCE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpShimmer()
        setUpRecyclerView(view)
        setUpObserver()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFacts(searchSentence)
    }

    private fun setUpRecyclerView(view: View) {
        factListRecyclerView = view.findViewById(R.id.facts_recycler_view)
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        factListRecyclerView.setHasFixedSize(true)
        factListRecyclerView.layoutManager = layoutManager
        factListRecyclerView.adapter = adapter
    }

    private fun setUpObserver() {
        viewModel.successLiveData.observe(
            viewLifecycleOwner,
            Observer {
                onSuccess(factList = it)
            }
        )

        viewModel.emptySuccessLiveData.observe(
            viewLifecycleOwner,
            Observer {
                onEmptySuccess()
            }
        )

        viewModel.errorLiveData.observe(
            viewLifecycleOwner,
            Observer {
                onError()
            }
        )
    }

    private fun setUpShimmer() {
        shimmerFrameLayout?.show()
    }

    private fun onSuccess(factList: List<Fact>) {
        Log.d("AAAAA", "onSuccess")
        shimmerFrameLayout?.hide()
        factListRecyclerView.visibility = View.VISIBLE
        adapter.updateFacts(factList)
    }

    private fun onEmptySuccess() {
        Log.d("AAAAA", "onEmptySuccess")
        val properties = SnackbarProperties(
            textResId = R.string.default_empty_message_snackbar,
            backgroundResId = R.color.orange
        )

        shimmerFrameLayout?.hide()
        view?.showSnackbar(properties)
    }

    private fun onError() {
        Log.d("AAAAA", "onError")
        shimmerFrameLayout?.hide()
        view?.showSnackbar()
    }

    companion object {
        private const val SEARCH_SENTENCE = "SEARCH_SENTENCE"

        fun newInstance(searchSentence: String) =
            FactListFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(SEARCH_SENTENCE, searchSentence)
                    }
                }
    }
}
