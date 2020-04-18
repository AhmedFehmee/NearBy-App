package com.fahmy.nearPlaces.repository.db.places

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fahmy.nearPlaces.repository.model.places.PlaceItem


@Dao
interface PlacesDao {

    /**
     * Insert places into the database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaces(articles: MutableList<PlaceItem>): List<Long>

    /**
     * Get all the places from database
     */
    @Query("SELECT * FROM places_table")
    fun getAllPlaces(): LiveData<List<PlaceItem>>

    @Query("DELETE FROM places_table")
    abstract fun deleteAllPlaces()
}