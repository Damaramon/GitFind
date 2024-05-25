package com.example.submission1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.databinding.FragmentFollowerBinding
private const val ARG_POSITION = "position"
private const val ARG_USERNAME = "username"
class Follower : Fragment() {
    private var position: Int = 0
    private var username: String = ""
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var followViewModel: UserViewModelFrag
    private lateinit var userFollowAdapter: UserFollowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        position = requireArguments().getInt(ARG_POSITION)
        username = requireArguments().getString(ARG_USERNAME) ?: ""

        followViewModel = ViewModelProvider(this).get(UserViewModelFrag::class.java)

        userFollowAdapter = UserFollowAdapter(emptyList())

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userFollowAdapter
        }

        observeUsers()
        fetchUsers()
    }

    private fun observeUsers() {
        followViewModel.userList.observe(viewLifecycleOwner, { users ->
            userFollowAdapter.setUserList(users)
            binding.loadingView.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        })
    }

    private fun fetchUsers() {
        if (position == 1) {
            followViewModel.fetchFollowers(username)
        } else {
            followViewModel.fetchFollowing(username)
        }
    }


}
