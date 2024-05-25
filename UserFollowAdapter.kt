package com.example.submission1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1.data.response.ItemsItem
import com.example.submission1.databinding.ItemReviewBinding

class UserFollowAdapter(private var userList: List<ItemsItem>) :
    RecyclerView.Adapter<UserFollowAdapter.UserFollowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserFollowViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserFollowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserFollowViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(users: List<ItemsItem>) {
        userList = users
        notifyDataSetChanged()
    }

    class UserFollowViewHolder(private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.apply {
                tvItemName.text = user.login
                Glide.with(root.context)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(imgItemPhoto)

            }
        }
    }
}
