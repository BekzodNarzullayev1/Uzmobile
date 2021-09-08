package com.example.uzmobile

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.uzmobile.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs.getString("My_Lang","")

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        supportActionBar?.hide()

        Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
            if (language==""){
                val lanIntent = Intent(this, LanguageActivity::class.java)
                this.startActivity(lanIntent)
                this.finish()
            }else{
                val lanIntent = Intent(this, MainActivity::class.java)
                this.startActivity(lanIntent)
                this.finish()
            }

        }, 1500)
    }
}