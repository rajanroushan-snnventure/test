package com.example.mytestapp.Details.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mytestapp.databinding.ActivityRepoDetailsBinding

class RepoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoDetailsBinding.inflate(layoutInflater) // Initialize binding
        setContentView(binding.root)

        // Get repository details from intent extras
        val repoName = intent.getStringExtra(EXTRA_REPO_NAME)
        val repoUrl = intent.getStringExtra(EXTRA_REPO_URL)

        binding.repoNameTextView.text = repoName
        binding.repoUrlTextView.text = repoUrl
    }

    companion object {
        const val EXTRA_REPO_NAME = "repo_name"
        const val EXTRA_REPO_URL = "repo_url"
    }
}
