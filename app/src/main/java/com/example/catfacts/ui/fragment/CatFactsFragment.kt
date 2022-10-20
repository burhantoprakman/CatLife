package com.example.catfacts.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.catfacts.R
import com.example.catfacts.adapter.CatFactAdapter
import com.example.catfacts.resources.Resources
import com.example.catfacts.ui.MainActivity
import com.example.catfacts.util.Constants
import com.example.catfacts.viewmodel.CatFactViewModel
import kotlinx.android.synthetic.main.fragment_cat_facts.*

class CatFactsFragment : Fragment(R.layout.fragment_cat_facts) {
    lateinit var viewModel: CatFactViewModel
    lateinit var catFactAdapter: CatFactAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).catFactViewModel
        setUpAdapter()

        //Observe cat facts list and pass it to adapter.
        viewModel.catFactsList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resources.Success -> {
                    response.data?.let { catFactResponse ->
                        catFactAdapter.list = catFactResponse

                        //Add +1 cause its int value and it will round up. Another +1 plus
                        // come last page will always be empty
                        val totalPages =
                            catFactResponse.size / Constants.QUERY_PAGE_SIZE_FOR_FACTS + 2

                        isLastPage = viewModel.catFactPageNumber == totalPages
                        //Check if its last page
                        if (isLastPage) {
                            //Remove padding from adapter to better view
                            rv_cats_facts.setPadding(0, 0, 0, 0)
                        }
                        //Notify adapter for changes
                        catFactAdapter.notifyDataSetChanged()
                    }
                    hideProgressBar()
                }
                is Resources.Error -> {
                    response.message?.let {
                        Toast.makeText(activity, "Error! :\n " + it, Toast.LENGTH_SHORT).show()
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
    }

    private fun setUpAdapter() {
        catFactAdapter = CatFactAdapter()
        rv_cats_facts.apply {
            adapter = catFactAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@CatFactsFragment.scrollListener)
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
                //While scrolling adapter , keep it true
                isScrolling = true
            }
        }

        //If its not loading, last page , lastItem, not first item , total more than visible
        // and scrolling then make a request for get next page's data else stop making request
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= Constants.QUERY_PAGE_SIZE_FOR_FACTS

            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotBeginning
                    && isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getCatFacts()
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