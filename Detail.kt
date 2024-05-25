
package com.example.submission1
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.submission1.data.ui.NewsViewModel
import com.example.submission1.data.ui.ViewModelFactory
import com.example.submission1.database.Note
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import com.example.submission1.databinding.ActivityDetailBinding
import com.example.submission1.databinding.ActivityMainBinding
import com.example.submission1.di.Injection
import okhttp3.internal.platform.android.BouncyCastleSocketAdapter.Companion.factory
import retrofit2.Converter

var isDataSaved: Boolean = false
class Detail : AppCompatActivity() {
    private lateinit var note: Note

    private lateinit var viewModel: UserViewModel
    private lateinit var viewModell: NewsViewModel
    private lateinit var factory: ViewModelFactory
    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter
    private lateinit var binding: ActivityDetailBinding
    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_AVATAR_URL = "extra_avatar_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        note = Note(
            username = "Contoh Username",
            avatarUrl = "https://contoh.com/avatar.png",
            isBookmarked = false// atau false, sesuai kebutuhan
        )
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Mengambil data dari intent
        val ivBookmark = binding.fab
        val login = intent.getStringExtra(EXTRA_NAME)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val text = "ID : $login"
        binding.usernameTextView.text = text
        note.username = text
        note.avatarUrl = avatarUrl
///////////
        factory = ViewModelFactory.getInstance(this)

        // Membuat instance dari NewsViewModel dengan menggunakan factory
        viewModell = ViewModelProvider(this, factory).get(NewsViewModel::class.java)

        setupUI()
        // Metode onCreate() dan kode lainnya




// ////////////////
        sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = "$login"

        binding.viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, binding.viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        viewModel.userData.observe(this) { userData ->
            binding.usernameTextView.text = userData.login
            Glide.with(this)
                .load(avatarUrl)
                .circleCrop()
                .into(binding.avatarImageView)
            binding.name.text = userData.name
            binding.followersTextView.text = "${userData.followers} Followers"
            binding.followingTextView.text = "${userData.following} Following "
        }

        login?.let { viewModel.fetchUserData(it) }
    }
    private fun setupUI() {
        val ivBookmark = binding.fab
        // Melakukan inisialisasi komponen UI
        // Contoh: val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        binding.fab.setOnClickListener {
            if ( note.isBookmarked == false) {
                viewModell.saveNews(note)
                ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_favorite_24))
                note.isBookmarked = true

            } else {
                note.isBookmarked = false
                viewModell.deleteNews(note)
                ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.baseline_favorite_border_24))
            }
        }
        // Membuat adapter dan mengaitkannya dengan RecyclerView
        val newsAdapter = NewsAdapter { news ->
            if (news.isBookmarked){
                viewModell.deleteNews(news)
            } else {
                viewModell.saveNews(news)
            }
        }

    }

}
