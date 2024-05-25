package com.example.submission1

import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submission1.data.response.ItemsItem
import com.example.submission1.data.response.ProfResponse
import com.example.submission1.databinding.ActivityMainBinding
import com.example.submission1.retrofit.ApiConfig
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback


class MainActivity : AppCompatActivity() {
    private lateinit var userAdapter: ReviewAdapter
    private lateinit var rvUserProfile: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.fab.setOnClickListener {
            // Intent untuk memulai aktivitas kedua (SecondActivity)
            val intent = Intent(this, News::class.java)
            startActivity(intent)
        }
        with(binding) {

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    fetchData(searchView.text.toString())
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }

        rvUserProfile = findViewById(R.id.rvReview)
        rvUserProfile.layoutManager = LinearLayoutManager(this)

        // buat bikin garis end tiap data
        val dividerItemDecoration = DividerItemDecoration(rvUserProfile.context, DividerItemDecoration.VERTICAL)
        rvUserProfile.addItemDecoration(dividerItemDecoration)

        userAdapter = ReviewAdapter(emptyList())

        rvUserProfile.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

        fetchData("dam")
    }

    private fun fetchData(query: String) {
        binding.progressBar.visibility = View.VISIBLE
        val apiService = ApiConfig().apiService
        apiService.getListUsers(query).enqueue(object : Callback<ProfResponse> {
            override fun onResponse(
                call: Call<ProfResponse>,
                response: retrofit2.Response<ProfResponse>
            ) {
                if (response.isSuccessful) {
                    binding.progressBar.visibility = View.INVISIBLE
                    response.body()?.let { userList ->
                        showData(userList.items)
                    }
                }
            }

            override fun onFailure(call: Call<ProfResponse>, t: Throwable) {

            }
        })
    }

    private fun showData(users: List<ItemsItem>) {
        userAdapter.userList = users
        userAdapter.notifyDataSetChanged()
    }
}