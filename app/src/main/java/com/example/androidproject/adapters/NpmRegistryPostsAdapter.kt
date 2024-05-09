package com.example.androidproject.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidproject.databinding.NpmRegistryPostItemBinding
import com.example.androidproject.helpers.extensions.setupLinksAdapter
import com.example.androidproject.models.npm.NpmRegistryRoot
import com.example.androidproject.models.npm.Object


internal class NpmRegistryPostsAdapter(
    private var root: NpmRegistryRoot,
    private val openLink: (Intent) -> Unit,
) :
    RecyclerView.Adapter<NpmRegistryPostsAdapter.NpmRegistryViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun setData(root: NpmRegistryRoot) {
        this.root = root
        notifyDataSetChanged()
    }


    inner class NpmRegistryViewHolder(private val binding: NpmRegistryPostItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
        fun bind(post: Object) {
            binding.tvOwnerName.text = "by ${post.packageField.publisher.username}"
            binding.tvPostVersion.text = "Version: ${post.packageField.version}"
            binding.tvPostTitle.text = post.packageField.name


            val links = listOf(
                post.packageField.links.npm,
                post.packageField.links.homepage,
                post.packageField.links.bugs,
                post.packageField.links.repository
            )
            binding.tvPostLinks.setupLinksAdapter(links, openLink)

            binding.popularityBar.rating = post.score.detail.popularity.times(5).toFloat()
            binding.tvPostTags.text = "Keywords: ${post.packageField.keywords.joinToString(",")}"
            binding.tvPostContent.text = post.packageField.description
            binding.tvPostContent.setOnClickListener {
                AlertDialog.Builder(binding.root.context)
                    .setTitle(post.packageField.name)
                    .setMessage(post.packageField.description)
                    .setNegativeButton("Close", null)
                    .show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NpmRegistryViewHolder {
        return NpmRegistryViewHolder(NpmRegistryPostItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return root.objects.size
    }

    override fun onBindViewHolder(holder: NpmRegistryViewHolder, position: Int) {
        val post = root.objects[position]
        holder.bind(post)
    }
}