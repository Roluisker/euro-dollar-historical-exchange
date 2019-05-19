package com.sc.lydianlion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sc.core.ui.coreComponent
import com.sc.lydianlion.di.DaggerMainActivityComponent

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencyInjection()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun initDependencyInjection() =
        DaggerMainActivityComponent
            .builder()
            .coreComponent(coreComponent())
            .build()
            .inject(this)

}
