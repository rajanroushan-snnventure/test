package com.example.mytestapp.Home.View

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mytestapp.Home.Model.GitRepo
import com.example.mytestapp.Details.View.RepoDetailActivity
import com.example.mytestapp.databinding.ItemGitRepoBinding

class GitRepoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
        const val PAGE_SIZE = 20
    }

    private val data = mutableListOf<GitRepo>()

    fun setData(newData: List<GitRepo>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun clear() {
        data.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGitRepoBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemGitRepoBinding.inflate(inflater, parent, false)
            return ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            val repo = data[position]
            holder.bind(repo)

            // Handle item click
            holder.itemView.setOnClickListener {
                val context: Context = holder.itemView.context
                val intent = Intent(context, RepoDetailActivity::class.java).apply {
                    putExtra(RepoDetailActivity.EXTRA_REPO_NAME, repo.name)
                    putExtra(RepoDetailActivity.EXTRA_REPO_URL, repo.url)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        return if (position == data.size - 1 && data.size >= PAGE_SIZE) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    class ViewHolder(private val binding: ItemGitRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        //private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        //private val urlTextView: TextView = itemView.findViewById(R.id.urlTextView)

        fun bind(repo: GitRepo) {
            binding.nameTextView.text = repo.name
            binding.urlTextView.text = repo.url
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
