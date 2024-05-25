package com.example.submission1

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission1.data.ui.NewsViewModel
import com.example.submission1.data.ui.ViewModelFactory
import com.example.submission1.database.Result
import com.example.submission1.databinding.ActivityNewsBinding

class News : AppCompatActivity() {
    companion object {
        const val ARG_TAB = "tab_name"
        const val TAB_NEWS = "news"
        const val TAB_BOOKMARK = "bookmark"
    }
    private var tabName: String? = null
    private lateinit var binding: ActivityNewsBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)

        val recyclerView = binding.rvNews
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        newsAdapter = NewsAdapter { news ->
            if (news.isBookmarked) {
                viewModel.deleteNews(news)
            } else {
                viewModel.saveNews(news)
            }
        }
        recyclerView.adapter = newsAdapter



        if (tabName == TAB_NEWS) {
            viewModel.getHeadlineNews().observe(this) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }

                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val newsData = result.data
                            newsAdapter.submitList(newsData)
                        }

                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                this@News,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
                    }
                }
            }
        }else if (tabName == TAB_BOOKMARK) {
                viewModel.getBookmarkedNews().observe(this) { bookmarkedNews ->
                    binding?.progressBar?.visibility = View.GONE
                    newsAdapter.submitList(bookmarkedNews)
                }
            }

            binding?.rvNews?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = newsAdapter
            }
        }

        override fun onDestroy() {
            super.onDestroy()

        }



    }
