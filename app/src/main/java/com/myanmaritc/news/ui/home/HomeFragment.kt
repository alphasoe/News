package com.myanmaritc.news.ui.home

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.myanmaritc.news.R
import com.myanmaritc.news.model.ArticlesItem
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(), HomeAdapter.OnClickListener {

    lateinit var homeViewModel: HomeViewModel

    lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        homeAdapter = HomeAdapter()
        homeAdapter.setOnClickListener(this)

        setRecyckerView()

        observeViewModel()

        swipeRefreshLayout.setOnRefreshListener {
            observeViewModel()
            setRecyckerView()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    fun setRecyckerView() {
        recyclerTopHeadlines.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }
    }

    fun observeViewModel() {

        swipeRefreshLayout.isRefreshing = false

        homeViewModel.getResult().observe(
            viewLifecycleOwner, Observer {
                homeAdapter.updateArticlesList(it.articles as List<ArticlesItem>)
            }
        )

        homeViewModel.getLoading().observe(
            viewLifecycleOwner, Observer { isLoading ->
                loadingProgress.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            }
        )

        homeViewModel.getErrorStatus().observe(
            viewLifecycleOwner, Observer { status ->
                if (status) {
                    homeViewModel.getErrorMessage().observe(
                        viewLifecycleOwner, Observer {
                            errorMessage.text = it
                        }
                    )
                }
            }
        )
    }

    override fun onResume() {
        super.onResume()

        homeViewModel.loadResult()
    }

    override fun onClick(articlesItem: ArticlesItem) {
        val directions = HomeFragmentDirections.actionHomeFragmentToDetailFragment(articlesItem)
        view?.findNavController()?.navigate(directions)
    }

}