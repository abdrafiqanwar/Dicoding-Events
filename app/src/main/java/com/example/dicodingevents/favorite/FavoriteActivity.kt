package com.example.dicodingevents.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.R
import com.example.dicodingevents.core.ui.EventAdapter
import com.example.dicodingevents.databinding.ActivityFavoriteBinding
import com.example.dicodingevents.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Favorite Events"

        val adapter = EventAdapter()
        adapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        binding.rvEvent.adapter = adapter

        viewModel.favoriteEvent.observe(this) { dataEvent ->
            adapter.submitList(dataEvent)
            binding.viewEmpty.root.visibility = if (dataEvent.isNotEmpty()) View.GONE else View.VISIBLE
        }
    }
}