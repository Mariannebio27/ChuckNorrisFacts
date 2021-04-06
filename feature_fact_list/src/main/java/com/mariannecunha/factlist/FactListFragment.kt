package com.mariannecunha.factlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mariannecunha.core.extensions.hide
import com.mariannecunha.core.extensions.show
import com.mariannecunha.domain.model.Fact
import com.mariannecunha.factlist.databinding.FragmentFactListBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FactListFragment : Fragment() {

    private val viewModel by viewModel<FactListViewModel>()
    private val adapter by inject<FactListAdapter>()
    lateinit var binding: FragmentFactListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fact_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpBinding(view)
        setUpShimmer()
        setUpRecyclerView()
        setUpObserver()
        setUpImageView(view)
    }

    private fun setUpImageView(view: View) {
        Glide.with(view.context)
            .load("https://api.chucknorris.io/img/chucknorris_logo_coloured_small@2x.png")
            .into(binding.welcomeView.chuckNorrisImageView)
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFacts()
    }

    private fun setUpBinding(view: View) {
        binding = FragmentFactListBinding.bind(view)
    }

    private fun setUpRecyclerView() = with(binding) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        factsRecyclerView.setHasFixedSize(true)
        factsRecyclerView.layoutManager = layoutManager
        factsRecyclerView.adapter = adapter
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

        viewModel.welcomeLiveData.observe(
            viewLifecycleOwner,
            Observer {
                onUserFirstTime()
            }
        )
    }

    private fun setUpShimmer() {
        binding.shimmerViewContainer.show()
    }

    private fun onSuccess(factList: List<Fact>) = with(binding) {
        adapter.updateFacts(factList)
        shimmerViewContainer.hide()
        errorView.errorLayout.hide()
        welcomeView.welcomeLayout.hide()
        emptyErrorView.emptyErrorLayout.hide()
        factsRecyclerView.show()
    }

    private fun onEmptySuccess() = with(binding) {
        shimmerViewContainer.hide()
        factsRecyclerView.hide()
        errorView.errorLayout.hide()
        welcomeView.welcomeLayout.hide()
        emptyErrorView.emptyErrorLayout.show()
    }

    private fun onError() = with(binding) {
        shimmerViewContainer.hide()
        factsRecyclerView.hide()
        emptyErrorView.emptyErrorLayout.hide()
        welcomeView.welcomeLayout.hide()
        errorView.errorLayout.show()
    }

    private fun onUserFirstTime() = with(binding) {
        shimmerViewContainer.hide()
        factsRecyclerView.hide()
        emptyErrorView.emptyErrorLayout.hide()
        errorView.errorLayout.hide()
        welcomeView.welcomeLayout.show()
    }
}
