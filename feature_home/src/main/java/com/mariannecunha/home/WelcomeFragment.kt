package com.mariannecunha.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.mariannecunha.search.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class WelcomeFragment : Fragment() {

    private val viewModel by sharedViewModel<WelcomeViewModel>()
    private lateinit var chuckNorrisImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpImageView(view)
        setUpWelcomeFragment(view)
    }

    private fun setUpWelcomeFragment(view: View) {
        view.setOnClickListener {
            onWelcomeClick()
        }
    }

    private fun setUpImageView(view: View) {
        chuckNorrisImageView = view.findViewById(R.id.chuck_norris_image_view)

        Glide.with(view.context)
            .load("https://api.chucknorris.io/img/chucknorris_logo_coloured_small@2x.png")
            .into(chuckNorrisImageView)
    }

    private fun onWelcomeClick() {
        viewModel.onWelcomeClick()
    }

    companion object {
        fun newInstance() = WelcomeFragment()
    }
}
