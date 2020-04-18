package com.fahmy.nearPlaces.di.modules

import com.fahmy.nearPlaces.ui.places.PlacesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * All your Activities participating in DI would be listed here.
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributePlacesActivity(): PlacesActivity

}
