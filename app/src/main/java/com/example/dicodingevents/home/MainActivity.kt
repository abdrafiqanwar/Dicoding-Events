package com.example.dicodingevents.home

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevents.R
import com.example.dicodingevents.core.ui.EventAdapter
import com.example.dicodingevents.databinding.ActivityMainBinding
import com.example.dicodingevents.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.title = "Dicoding Events"

        val adapter = EventAdapter()
        adapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }

        binding.rvEvent.layoutManager = LinearLayoutManager(this)
        binding.rvEvent.adapter = adapter

        viewModel.event.observe(this) { event ->
            if (event != null) {
                when (event) {
                    is com.example.dicodingevents.core.data.Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is com.example.dicodingevents.core.data.Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        adapter.submitList(event.data)
                    }
                    is com.example.dicodingevents.core.data.Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = event.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val uri = Uri.parse("eventapp://event")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        return true
    }

    private fun registerBroadCastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    Intent.ACTION_POWER_CONNECTED -> {
//                        tvPowerStatus.text = getString(R.string.power_connected)
                    }
                    Intent.ACTION_POWER_DISCONNECTED -> {
//                        tvPowerStatus.text = getString(R.string.power_disconnected)
                    }
                }
            }
        }
        val intentFilter = IntentFilter()
        intentFilter.apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadCastReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }
}