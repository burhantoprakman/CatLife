package com.example.catfacts.ui.fragment

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.R
import com.example.catfacts.adapter.CatBreedsAdapter
import com.example.catfacts.resources.Resources
import com.example.catfacts.ui.MainActivity
import com.example.catfacts.util.Constants.Companion.QUERY_PAGE_SIZE_FOR_BREEDS
import com.example.catfacts.viewmodel.CatBreedsViewModel
import kotlinx.android.synthetic.main.fragment_cat_breeds.*

class CatBreedsFragment : Fragment(R.layout.fragment_cat_breeds) {
    lateinit var viewModel: CatBreedsViewModel
    lateinit var catsBreedsAdapter: CatBreedsAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).catBreedsViewModel

        setUpAdapter()

        //Observe list from viewmodel
        viewModel.catBreedsList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resources.Success -> {
                    response.data?.let { catBreedsResponse ->
                        catsBreedsAdapter.list = catBreedsResponse.data
                        val totalPages = catBreedsResponse.total / QUERY_PAGE_SIZE_FOR_BREEDS + 2
                        isLastPage = viewModel.catBreedsPageNumber == totalPages
                        if (isLastPage) {
                            rv_cats_breeds.setPadding(0, 0, 0, 0)
                        }
                        catsBreedsAdapter.notifyDataSetChanged()
                    }
                    hideProgressBar()
                }
                is Resources.Error -> {
                    response.message?.let {
                        Toast.makeText(activity, "Error: \n" + it, Toast.LENGTH_SHORT).show()
                        hideProgressBar()
                    }
                }
                is Resources.Loading -> {
                    showProgressBar()
                }
                else -> {
                    // DO NOTHING
                }
            }
        }

        //Adapter click listener
        catsBreedsAdapter.setOnItemClickListener {
            //Nav component action with arguments
            val action = CatBreedsFragmentDirections.actionCatBreedsFragmentToCatDetailFragment(it)
            findNavController().navigate(action)

        }
    }

    private fun setUpAdapter() {
        catsBreedsAdapter = CatBreedsAdapter()
        rv_cats_breeds.apply {
            adapter = catsBreedsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@CatBreedsFragment.scrollListener)
        }
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    //Scroll listener for listen adapter's scroll
    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        /*If its not loading, last page , lastItem, not first item , total more than visible
         and scrolling then make a request for get next page's data else stop making request*/
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE_FOR_BREEDS
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getCatBreeds()
                isScrolling = false
            }
        }
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
        isLoading = false
    }
}