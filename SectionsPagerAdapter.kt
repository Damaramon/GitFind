package com.example.submission1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String = ""
    override fun createFragment(position: Int): Fragment {
        val fragment = Follower()
        fragment.arguments = Bundle().apply {
            if(position==3){
                putString(ARG_USERNAME, username)
            }
            putInt(ARG_POSITION, position + 1)
            putString(ARG_USERNAME, username)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}