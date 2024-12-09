package com.example.dicodingevents.detail

import android.os.Bundle
import android.text.Html
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat.getParcelableExtra
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.dicodingevents.R
import com.example.dicodingevents.core.data.source.local.entity.EventEntity
import com.example.dicodingevents.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailEvent = getParcelableExtra(intent, EXTRA_DATA, EventEntity::class.java)
        showDetailEvent(detailEvent)
    }

    private fun showDetailEvent(detailEvent: EventEntity?) {
        detailEvent?.let {
            Glide.with(this)
                .load(detailEvent.mediaCover)
                .into(binding.ivMediaCover)
            binding.tvDesc.text = Html.fromHtml(detailEvent.description, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}