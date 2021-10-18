package com.example.tugasakhir_kmmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugasakhir_kmmi.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btInput.setOnClickListener {
            startActivity(Intent(this@MainActivity,simpan::class.java))
        }

        binding.btLihat.setOnClickListener {
            startActivity(Intent(this@MainActivity,tampil::class.java))
        }
    }

}