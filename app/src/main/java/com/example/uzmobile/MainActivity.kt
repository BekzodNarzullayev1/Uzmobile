package com.example.uzmobile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.uzmobile.databinding.ActivityMainBinding
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.util.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var info: String
    lateinit var title: String
    lateinit var back: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs?.getString("My_Lang", "")

        when (language) {
            "uz" -> {
                title = "Biz haqimizda"
                back = "orqaga"
                info =
                    "Bu ilova android kursini muvaffaqiyatli tugatganligini bildiruvchi maxsus sertifikatni olish maqsadida tayyorlandi. Kamchiliklar uchun oldindan uzur. Fikr va mulohazalaringizni biz bilan aloqa bo'limi orqali yuborishingiz mumkin."
            }
            "ru" -> {
                title = "о нас"
                back = "назад"
                info =
                    "Это приложение было разработано для получения специального сертификата об успешном прохождении курса Android. Заранее извиняюсь за недостатки. Вы можете отправлять свои комментарии и отзывы через наш раздел контактов."
            }
            else -> {
                title = "Биз ҳақимизда"
                back = "орқага"
                info =
                    "Бу илова андроид курсини муваффақиятли тугатганлигини билдирувчи махсус сертификатни олиш мақсадида тайёрланди. Камчиликлар учун олдиндан узур. Фикр ва мулоҳазаларингизни биз билан алоқа бўлими орқали юборишингиз мумкин."
            }
        }


//        val intent = intent
//        val lang = intent.getStringExtra("lang")
//        if(lang!=null){
//            setLocale(lang)
//        }

        val drawer = binding.drawer

        setSupportActionBar(binding.toolbar)
        val drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val currentDestination = findNavController(R.id.my_nav_host_fragment).currentDestination

//        //hide toolbar
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        //permission
        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.CALL_PHONE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.isAnyPermissionPermanentlyDenied) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri = Uri.fromParts("package", this@MainActivity.packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()

        binding.apply {

            balanceBtn.setOnClickListener {
                val uri = "tel:*100*1${Uri.encode("#")}"
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse(uri)
                startActivity(intent)
            }
            notificationBtn.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://uztelecom.uz/"))
                startActivity(browserIntent)
            }
            operatorBtn.setOnClickListener {
                if (currentDestination?.id != R.id.operatorFragment) {
                    findNavController(R.id.my_nav_host_fragment).navigate(R.id.operatorFragment)
                }
            }
            settingBtn.setOnClickListener {

                val intent = Intent(this@MainActivity, LanguageActivity::class.java)
                this@MainActivity.startActivity(intent)
                this@MainActivity.finish()
            }
            homeFab.setOnClickListener {
                findNavController(R.id.my_nav_host_fragment).navigate(R.id.homeFragment)
            }

        }

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_about -> {
                    binding.drawer.closeDrawer(GravityCompat.START)
                    val alertDialog = AlertDialog.Builder(this)
                    alertDialog
                        .setTitle("$title")
                        .setMessage("$info")
                        .setPositiveButton(
                            "$back"
                        ) { _, _ ->

                        }
                        .show()
                }
                R.id.nav_tg -> {
                    binding.drawer.closeDrawer(GravityCompat.START)
                    val intent =
                        Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=ussduz"))
                    startActivity(intent)
                }
                R.id.nav_share -> {
                    binding.drawer.closeDrawer(GravityCompat.START)
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    startActivity(shareIntent)
                }
                R.id.nav_call -> {
                    binding.drawer.closeDrawer(GravityCompat.START)
                    val addresses = listOf(
                        "bekzodnarzullayev89@gmail.com"
                    ).toTypedArray()
                    val subject = "Some title"
                    val text = "Some message\n\nNew Line\n\n"
                    mailtoTypeEmailCreation(addresses, subject, text)
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                binding.drawer.openDrawer(GravityCompat.START)
                true
            }
            R.id.action_share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=uz.pdp.uzmobile"
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
                true
            }
            R.id.action_tg -> {
                val intent =
                    Intent(Intent.ACTION_VIEW, Uri.parse("tg://resolve?domain=ussduz"))
                startActivity(intent)
                true
            }
            else -> {
                binding.drawer.openDrawer(GravityCompat.START)
                true
            }
        }
    }


    override fun onBackPressed() {

        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)
        } else {
//            super.onBackPressed()
            val currentDestination = findNavController(R.id.my_nav_host_fragment).currentDestination
            if (currentDestination?.id == R.id.homeFragment) {
                finish()
            } else {
                findNavController(R.id.my_nav_host_fragment).navigate(R.id.homeFragment)
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    private fun mailtoTypeEmailCreation(
        addresses: Array<String>, subject: String, text: String
    ) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            val mailto = "mailto:" + addresses.joinToString(",")
            data = Uri.parse(mailto)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, text)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor: SharedPreferences.Editor = getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    fun loadLocale() {
        val prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = prefs.getString("My_Lang", "")
        setLocale(language!!)
    }
}