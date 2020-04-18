package com.fahmy.nearPlaces.repository.repo.places

import android.content.Context
import androidx.lifecycle.LiveData
import com.fahmy.nearPlaces.app.AppExecutors
import com.fahmy.nearPlaces.repository.api.ApiServices
import com.fahmy.nearPlaces.repository.api.network.NetworkAndDBBoundResource
import com.fahmy.nearPlaces.repository.api.network.NetworkResource
import com.fahmy.nearPlaces.repository.api.network.Resource
import com.fahmy.nearPlaces.repository.db.places.PlacesDao
import com.fahmy.nearPlaces.repository.model.places.Place
import com.fahmy.nearPlaces.repository.model.places.PlaceItem
import com.fahmy.nearPlaces.utils.AppUtils
import com.fahmy.nearPlaces.utils.ConnectivityUtil
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PlacesRepository @Inject constructor(private val placesDao: PlacesDao, private val apiServices: ApiServices, private val context: Context, private val appExecutors: AppExecutors = AppExecutors()) {

    /**
     * Fetch the Places from database if exist else fetch from web
     * and persist them in the database
     */
    fun getPlaces(gpsLat: Double?, gpsLon: Double?): LiveData<Resource<List<PlaceItem>?>> {

        val data = HashMap<String, String>()
        data["client_id"] = AppUtils.CLINT_ID
        data["client_secret"] = AppUtils.SECRET_ID
        data["v"] = "20190211"
        data["ll"] = "$gpsLat,$gpsLon"
        data["radius"] = "1000"
        data["limit"] = "30"

        return object : NetworkAndDBBoundResource<List<PlaceItem>, Place>(appExecutors) {
            override fun saveCallResult(item: Place) {
                val list = item.response?.groups?.get(0)?.items

                if (list?.isNotEmpty() == true) {
                    placesDao.deleteAllPlaces()
                    val adapterList: MutableList<PlaceItem> = arrayListOf()

                    for (i in list.orEmpty()) {

                        val placeItem = PlaceItem(
                            0, i.venue?.name,
                            i.venue?.categories?.get(0)?.name,
                            i.venue?.location?.address,
                            "${i.venue?.categories?.get(0)?.icon?.prefix}/100x100/${i.venue?.categories?.get(0)?.icon?.prefix}"
                        )
                        adapterList.add(placeItem)
                    }
                    placesDao.insertPlaces(adapterList)
                }
            }

            override fun shouldFetch(data: List<PlaceItem>?) =
                (ConnectivityUtil.isConnected(context))

            override fun loadFromDb() = placesDao.getAllPlaces()

            override fun createCall() = apiServices.getPlaces(data, true)

        }.asLiveData()
    }

    fun getPlacesFromDBOnly(): LiveData<List<PlaceItem>> {
        return placesDao.getAllPlaces()
    }

    /**
     * Fetch the Places from database if exist else fetch from web
     * and persist them in the database
     */

    fun getPlacesFromServerOnly(): LiveData<Resource<Place>> {

        val data = HashMap<String, String>()
        data["client_id"] = AppUtils.CLINT_ID
        data["client_secret"] = AppUtils.SECRET_ID
        data["v"] = "20190211"
        data["ll"] = "30.06266117992451,31.249009521892646"
        data["radius"] = "500"

        return object : NetworkResource<Place>() {
            override fun createCall(): LiveData<Resource<Place>> {
                return apiServices.getPlaces(data, true)
            }

        }.asLiveData()
    }
}
