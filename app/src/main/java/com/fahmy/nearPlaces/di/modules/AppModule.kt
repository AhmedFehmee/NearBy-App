package com.fahmy.nearPlaces.di.modules

import android.content.Context
import android.content.res.Resources
import androidx.room.Room
import com.fahmy.nearPlaces.app.App
import com.fahmy.nearPlaces.repository.api.ApiServices
import com.fahmy.nearPlaces.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import com.fahmy.nearPlaces.repository.db.AppDatabase
import com.fahmy.nearPlaces.repository.db.places.PlacesDao
import com.fahmy.nearPlaces.utils.AppUtils
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module(includes = [PrefrencesModule::class, ActivityModule::class, ViewModelModule::class, PrefrencesModule::class])
class AppModule {


    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun providePlacesService(): ApiServices {
        return Retrofit.Builder()
            .baseUrl(AppUtils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .build()
            .create(ApiServices::class.java)
    }

    /**
     * Provides app AppDatabase
     */
    @Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "places-db")
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Provides PlacesDao an object to access Countries table from Database
     */
    @Singleton
    @Provides
    fun providePlacesDao(db: AppDatabase): PlacesDao {
        return db.placesDao()
    }

    /**
     * Application application level context.
     */
    @Singleton
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }

    /**
     * Application resource provider, so that we can get the Drawable, Color, String etc at runtime
     */
    @Provides
    @Singleton
    fun providesResources(application: App): Resources = application.resources
}
