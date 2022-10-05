package com.umar.storyappfinal.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.umar.storyappfinal.R
import com.umar.storyappfinal.adapter.LoadingStateAdapter
import com.umar.storyappfinal.adapter.StoryAdapter
import com.umar.storyappfinal.databinding.ActivityMainBinding
import com.umar.storyappfinal.factoryviewmodel.StroyFactoryViewModel
import com.umar.storyappfinal.ui.login.LoginActivity
import com.umar.storyappfinal.ui.maps.MapsActivity
import com.umar.storyappfinal.ui.story.AddStoryActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupVM()
    }

    private fun setupVM() {
        if (application.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvStory.layoutManager = GridLayoutManager(this, 1)
        } else {
            binding.rvStory.layoutManager = LinearLayoutManager(this)
        }

        val factory: StroyFactoryViewModel = StroyFactoryViewModel.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
        mainViewModel.isLogin().observe(this) {
            if (!it) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        mainViewModel.getToken().observe(this) { token ->
            if (token.isNotEmpty()) {
                if (token.isNotEmpty()) {
                    val adapter = StoryAdapter()
                    binding.rvStory.adapter = adapter.withLoadStateFooter(
                        footer = LoadingStateAdapter {
                            adapter.retry()
                        }
                    )
                    mainViewModel.getStories(token).observe(this) { result ->
                        adapter.submitData(lifecycle, result)
                    }
                }
            }

        }
    }

    private fun loading(b: Boolean) {
        binding.progressBar.visibility = if (b) View.VISIBLE else View.GONE

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_story -> {
                startActivity(Intent(this, AddStoryActivity::class.java))
                true
            }
            R.id.maps -> {
                startActivity(Intent(this, MapsActivity::class.java))
                true
            }
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logout -> {
                mainViewModel.logout()
                true
            }

            else -> true
        }
    }


}