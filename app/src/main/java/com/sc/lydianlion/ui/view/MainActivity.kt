package com.sc.lydianlion.ui.view

import android.os.Bundle
import androidx.navigation.findNavController
import com.sc.lydianlion.core.BaseActivity
import com.sc.lydianlion.R

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val a: ConvertFragment
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.moviesNavigationHost).navigateUp()

}
