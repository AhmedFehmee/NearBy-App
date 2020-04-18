package com.fahmy.nearPlaces.utils.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.fahmy.nearPlaces.ui.BaseActivity


/**
 * Synthetic sugaring to get instance of [ViewModel] for [AppCompatActivity].
 */
inline fun <reified T : ViewModel> BaseActivity.getViewModel(): T {
    return ViewModelProviders.of(this, viewModelFactory).get(T::class.java)
}

/**
 * Synthetic sugaring to get instance of [ViewModel] for [Fragment].
 */
inline fun <reified T : ViewModel> Fragment.getViewModel(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}
