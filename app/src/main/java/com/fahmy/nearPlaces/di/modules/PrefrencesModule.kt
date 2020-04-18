package com.fahmy.nearPlaces.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.fahmy.nearPlaces.utils.AppUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PrefrencesModule {

    /**
     * Provides Preferences object with MODE_PRIVATE
     */
    @Provides
    @Singleton
    fun provideSharedPreference(app: Application): SharedPreferences =
        app.getSharedPreferences(AppUtils.PREF_NAME, Context.MODE_PRIVATE)

}
