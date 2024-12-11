package com.example.dicodingevents.detail

import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat.getParcelableExtra
import com.bumptech.glide.Glide
import com.example.dicodingevents.R
import com.example.dicodingevents.core.domain.model.Event
import com.example.dicodingevents.databinding.ActivityDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail Event"

        val detailEvent = getParcelableExtra(intent, EXTRA_DATA, com.example.dicodingevents.core.domain.model.Event::class.java)
        showDetailEvent(detailEvent)
    }

    private fun showDetailEvent(detailEvent: com.example.dicodingevents.core.domain.model.Event?) {
        detailEvent?.let {
            Glide.with(this)
                .load(detailEvent.mediaCover)
                .into(binding.ivMediaCover)
            binding.tvDesc.text = Html.fromHtml(detailEvent.description, Html.FROM_HTML_MODE_COMPACT)

            var statusFavorite = detailEvent.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                viewModel.setFavoriteEvent(detailEvent, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_24))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.baseline_favorite_border_24))
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}