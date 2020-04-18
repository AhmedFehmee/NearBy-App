package com.fahmy.nearPlaces.repository.api

import androidx.lifecycle.LiveData
import com.fahmy.nearPlaces.repository.api.network.Resource
import com.fahmy.nearPlaces.repository.model.places.Place
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Api services to communicate with server
 *
 */
interface ApiServices {

    /**
     * Fetch Places from FS using GET API Call
     */
    @GET("venues/explore")
    fun getPlaces(
        @QueryMap options: Map<String, String>,
        @Query("sortByDistance") sortByDistance: Boolean
    ): LiveData<Resource<Place>>
}
