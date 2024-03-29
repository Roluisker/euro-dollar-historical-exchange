package com.sc.core.ui

import android.view.View
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar

fun Fragment.coreComponent() = requireActivity().coreComponent()

inline fun <reified V : ViewModel> Fragment.getViewModel(key: String? = null, noinline factory: () -> V): V {
    @Suppress("UNCHECKED_CAST")
    val viewModelProviderFactory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = factory() as T
    }

    return if (key != null) {
        ViewModelProviders.of(this, viewModelProviderFactory).get(key, V::class.java)
    } else {
        ViewModelProviders.of(this, viewModelProviderFactory).get(V::class.java)
    }
}

fun View.showSnackbar(snackbarText: String, timeLength: Int) {
    Snackbar.make(this, snackbarText, timeLength).show()
}
