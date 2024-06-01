package com.example.mytestapp.Home.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mytestapp.Home.Model.GitRepo
import com.example.mytestapp.Home.View.GitRepoAdapter

class GitRepoViewModel : ViewModel() {

    private val _reposLiveData = MutableLiveData<List<GitRepo>>()
    val reposLiveData: LiveData<List<GitRepo>> = _reposLiveData

    private val _errorLiveData = MutableLiveData<Throwable>()
    val errorLiveData: LiveData<Throwable> = _errorLiveData

    private val allRepos = mutableListOf<GitRepo>()
    private var currentPage = 1
    private var isLoading = false

    fun searchGitRepositories(query: String, context: Context) {
        currentPage = 1
        allRepos.clear()
        searchGitRepositories(query, currentPage, context)
    }

    fun loadMoreResults(query: String, context: Context) {
        currentPage++
        searchGitRepositories(query, currentPage, context)
    }

    private fun searchGitRepositories(query: String, page: Int, context: Context) {
        isLoading = true
        val searchResults = mutableListOf<GitRepo>()
        val startIndex = (page - 1) * GitRepoAdapter.PAGE_SIZE
        val endIndex = startIndex + GitRepoAdapter.PAGE_SIZE.coerceAtMost(100)
        for (i in startIndex until endIndex) {
            searchResults.add(GitRepo("Repo $i", "https://github.com/repo$i"))
        }
        allRepos.addAll(searchResults)
        _reposLiveData.value = allRepos
        isLoading = false
    }

    fun isLoading() = isLoading
}