package com.example.uzmobile

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.uzmobile.databinding.ActivityLanguageBinding
import java.util.*

class LanguageActivity : AppCompatActivity() {
    lateinit var binding:ActivityLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //hide toolbar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //hide action bar
        supportActionBar?.hide()

        binding.btnUzb.setOnClickListener {
            setLocale("uz")
            val intent = Intent(this,MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
        binding.btnRus.setOnClickListener {
            setLocale("ru")
            val intent = Intent(this,MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
        binding.btnKrill.setOnClickListener {
            setLocale("kk")
            val intent = Intent(this,MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }

    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
        val editor:SharedPreferences.Editor =  getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_Lang",lang)
        editor.apply()
    }

    fun loadLocale(){
        val prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs.getString("My_Lang","")
        setLocale(language!!)
    }

    override fun onBackPressed() {
        val prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs.getString("My_Lang","")
        if (language==""){
            finish()
        }else{
            val intent = Intent(this,MainActivity::class.java)
            this.startActivity(intent)
            this.finish()
        }
    }
}