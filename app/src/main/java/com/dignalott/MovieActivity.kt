package com.dignalott

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dignalott.adapter.MovieListAdapter
import com.dignalott.adapter.PagingListener
import com.dignalott.databinding.ActivityMainBinding
import com.dignalott.models.Content
import com.dignalott.models.Movies
import com.dignalott.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieActivity : AppCompatActivity() , PagingListener {

    private lateinit var viewBinding : ActivityMainBinding
    private lateinit var viewModel :  MainViewModel
    private lateinit var adapter : MovieListAdapter
    private  var searchMovies: Movies = Movies.emptyMovies

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_DignalOttApp)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title="Romantic Comedy"



        initViews()

//        Checking the orientation
        val orientation = resources.configuration.orientation

        if(orientation== ORIENTATION_LANDSCAPE)
        {
            viewBinding.idListRecycler.layoutManager=GridLayoutManager(this,7)
        }
        else{
            viewBinding.idListRecycler.layoutManager=GridLayoutManager(this,3)
        }


        viewModel.moviesList.observe(this) { moviesList->

            Log.d(" Json Data ", moviesList.toString())
            adapter.updateList(moviesList)
            adapter.notifyDataSetChanged()
        }

    }

    private fun initViews() {
        adapter= MovieListAdapter(null,this,this)
        viewBinding.idListRecycler.adapter=adapter
        viewModel=ViewModelProvider(this).get(MainViewModel::class.java)
        if(viewModel.moviesList.value!= Movies.emptyMovies)
        {
            viewModel.getMovieList(baseContext)
        }

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu,menu)

        val menuItem=menu?.findItem(R.id.id_search)
        val search = menuItem?.actionView as SearchView
        search.queryHint="Search Movies..."

        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                (searchMovies.page.`content-items`.content as ArrayList<Content>).clear()
                if(newText.length >= 3) {

                    viewModel.moviesList.value?.page?.`content-items`?.content?.filter {
                        it.name.lowercase().contains(
                            newText.lowercase()
                        )
                    }?.mapIndexed{
                            index,value ->
                        (searchMovies.page.`content-items`.content as ArrayList).add(value)
                    }

                    adapter.updateList(searchMovies)
                    Log.d("Movies","${searchMovies}")
                    adapter.notifyDataSetChanged()
                }
                else if(newText.length<3)
                {
                    viewModel.moviesList.value?.let { adapter.updateList(it) }
                    adapter.notifyDataSetChanged()
                }
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home ->
            {
                finish()
            }

        }
        return true
    }

    override fun startPaging() {
        if(!viewModel.secondLoaded)
        {
            viewModel.loadSecond(this)
            viewModel.moviesList.value?.let { adapter.updateList(it) }
        }
        if(!viewModel.thirdLoaded)
        {
            viewModel.loadThird(this)
            viewModel.moviesList.value?.let { adapter.updateList(it) }
        }

    }


}



















