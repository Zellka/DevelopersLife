package com.example.zelinskaya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.zelinskaya.adapter.PagerAdapter
import com.example.zelinskaya.databinding.ActivityMainBinding
import io.paperdb.Paper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Paper.init(this)

        binding.viewPager.adapter = PagerAdapter(this, supportFragmentManager)
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }
}