package com.sc.lydianlion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sc.core.ui.coreComponent
import com.sc.lydianlion.di.DaggerMainActivityComponent
import android.view.Menu
import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import com.google.android.instantapps.InstantApps

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencyInjection()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Euro to Dollar"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.default_menu, menu)
        when {
            InstantApps.isInstantApp(applicationContext) -> (menu.findItem(R.id.isolatedModule) as MenuItem).isVisible =
                false
            else -> (menu.findItem(R.id.isolatedModule) as MenuItem).isVisible = true
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.isolatedModule -> {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://lydian-lion-instant.com/convert")
                )
                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initDependencyInjection() =
        DaggerMainActivityComponent
            .builder()
            .coreComponent(coreComponent())
            .build()
            .inject(this)

}
