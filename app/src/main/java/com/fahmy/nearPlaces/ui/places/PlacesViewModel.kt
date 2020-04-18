package com.fahmy.nearPlaces.ui.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.fahmy.nearPlaces.repository.api.network.Resource
import com.fahmy.nearPlaces.repository.model.places.PlaceItem
import com.fahmy.nearPlaces.repository.repo.places.PlacesRepository
import javax.inject.Inject

class PlacesViewModel @Inject constructor(private val placesRepository: PlacesRepository) : ViewModel() {

    /**
     * Loading places from internet and database
     */
    fun getPlaces(gpsLat: Double?, gpsLon: Double?): LiveData<Resource<List<PlaceItem>?>> =
        placesRepository.getPlaces(gpsLat,gpsLon)

    /**
     * Loading places from internet only
     */
    private fun getPlacesFromOnlyServer() = placesRepository.getPlacesFromServerOnly()

    /**
     * Loading places from DB only
     */
    private fun getPlacesFromDBOnly() = placesRepository.getPlacesFromDBOnly()
}