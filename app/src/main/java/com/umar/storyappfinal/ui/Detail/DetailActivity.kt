package com.umar.storyappfinal.ui.Detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.umar.storyappfinal.databinding.ActivityDetailBinding
import com.umar.storyappfinal.model.ListStoryItem

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDetail()
    }

    private fun setupDetail() {
        val stroy = intent.getParcelableExtra<ListStoryItem>(EXTRA_STORY)
        binding.apply {
            nameDe.text = stroy?.name
            desDe.text = stroy?.description
        }
        Glide.with(this)
            .load(stroy?.photoUrl)
            .into(binding.imageSty)

    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}