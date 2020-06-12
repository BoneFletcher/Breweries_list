package com.sdv.brewerieslist.ui.breweries

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdv.brewerieslist.R
import com.sdv.brewerieslist.data.breweries.Breweries
import com.sdv.brewerieslist.data.utills.visibleOrGone
import kotlinx.android.synthetic.main.activity_breweries.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel


class BreweriesActivity : AppCompatActivity(), BreweriesAdapter.OnItemClickListener {

    private lateinit var adapter: BreweriesAdapter
    private lateinit var adapterSearch: BreweriesAdapter
    private val breweriesViewModel by viewModel<BreweriesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_breweries)
        setRecyclerView()
        observeChanges()
    }

    override fun onShowMapClick(model: Breweries) {
        val uri: String = "http://maps.google.com/maps?q="+ model.latitude+","+ model.longitude
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        startActivity(intent)
    }

    private fun setRecyclerView() {
        rv_breweries_list.layoutManager = LinearLayoutManager(this)
        adapter = BreweriesAdapter(this)
        rv_breweries_list.adapter = adapter
        rv_breweries_search_list.layoutManager = LinearLayoutManager(this)
        adapterSearch = BreweriesAdapter(this)
        rv_breweries_search_list.adapter = adapterSearch
        swipe_refresh_breweries.setOnRefreshListener { breweriesViewModel.refresh() }
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun observeChanges() {
        breweriesViewModel.breweriesLoadLiveData.observe(this, Observer {
            swipe_refresh_breweries.isRefreshing=false
            adapter.submitList(it)
        })
 
        breweriesViewModel.searchLiveData.observe(this, Observer {
            progress_bar_search.visibility = GONE
            adapterSearch.submitList(it)
        })

        breweriesViewModel.progressUpdateLiveData.observe(this, Observer {
          progress_bar_search.visibleOrGone(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as androidx.appcompat.widget.SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {

                    breweriesViewModel.breweriesSearchLoadLiveData.postValue(newText)
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    breweriesViewModel.breweriesSearchLoadLiveData.value = query
                    return false
                }
            })

        searchView.setOnSearchClickListener {
            rv_breweries_search_list.visibility=VISIBLE
            rv_breweries_list.visibility=GONE
        }

        searchView.setOnCloseListener(object : SearchView.OnCloseListener,
            androidx.appcompat.widget.SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                rv_breweries_search_list.visibility=GONE
                rv_breweries_list.visibility=VISIBLE
                adapterSearch.clearAdapter()
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
