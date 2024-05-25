package com.example.submission1

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.submission1.R.layout.item_review
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission1.data.response.ItemsItem



class ReviewAdapter(var userList: List<ItemsItem>) :
    RecyclerView.Adapter<ReviewAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(item_review, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.bind(user)
        holder.itemView.setOnClickListener {

            val context = holder.itemView.context
            val moveWithDataIntent = Intent(context,Detail::class.java)
            moveWithDataIntent.putExtra(Detail.EXTRA_AVATAR_URL, user.avatarUrl)
            moveWithDataIntent.putExtra(Detail.EXTRA_NAME, user.login)

            startActivity(context, moveWithDataIntent, null)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: ItemsItem) {
            itemView.findViewById<TextView>(R.id.tv_item_name).text = user.login
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .circleCrop()
                .into(itemView.findViewById<ImageView>(R.id.img_item_photo))
        }
    }
}