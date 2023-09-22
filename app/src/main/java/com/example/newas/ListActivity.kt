package com.example.newas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.newas.databinding.ActivityListBinding

class ListActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bt1.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
        binding.bt2.setOnClickListener {
            startActivity(Intent(this,ModifierActivity::class.java))
        }
    }
}