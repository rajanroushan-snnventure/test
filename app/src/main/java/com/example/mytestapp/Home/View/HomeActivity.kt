package com.example.mytestapp.Home.View

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.Home.Model.GitRepo
import com.example.mytestapp.Home.ViewModel.GitRepoViewModel
import com.example.mytestapp.databinding.ActivityMainBinding

class HomeActivity : AppCompatActivity() {

    lateinit var adapter: GitRepoAdapter
    val viewModel: GitRepoViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)
        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = GitRepoAdapter()
        binding.recyclerView.adapter = adapter

        // Observe LiveData for search results
        viewModel.reposLiveData.observe(this) { repos -> handleResults(repos) }
        viewModel.errorLiveData.observe(this) { error -> handleError(error) }

        // Set up search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchGitRepositories(query, this@HomeActivity)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    adapter.clear()
                }
                return false
            }
        })

        // Pagination
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!viewModel.isLoading() &&
                    (visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                    firstVisibleItemPosition >= 0 && totalItemCount >= GitRepoAdapter.PAGE_SIZE
                ) {
                    viewModel.loadMoreResults(binding.searchView.query.toString(), this@HomeActivity)
                }
            }
        })
    }

    private fun handleResults(repos: List<GitRepo>) {
        if (repos.isEmpty()) {
            //string should be in string.xml
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show()
        } else {
            adapter.setData(repos)
        }
    }

    private fun handleError(error: Throwable) {
        // Handle errors here
        Toast.makeText(this, error.message.toString(), Toast.LENGTH_LONG).show()
    }
}
