package com.example.submission1

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submission1.database.Note
import com.example.submission1.databinding.ItemFavBinding


class NewsAdapter(private val onBookmarkClick: (Note) -> Unit) : ListAdapter<Note, NewsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemFavBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val news = getItem(position)
        holder.bind(news)

        val ivBookmark = holder.binding.ivBookmark
        if (news.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmarked_white))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_white))
        }
        ivBookmark.setOnClickListener {
            onBookmarkClick(news)
        }
    }

    class MyViewHolder(val binding: ItemFavBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(news: Note) {
            binding.tvItemName.text = news.username

            Glide.with(itemView.context)
                .load(news.avatarUrl)
                .into(binding.imgItemPhoto)

        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Note> =
            object : DiffUtil.ItemCallback<Note>() {
                override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem.username == newItem.username
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
