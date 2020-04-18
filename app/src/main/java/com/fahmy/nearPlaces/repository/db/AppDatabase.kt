package com.fahmy.nearPlaces.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fahmy.nearPlaces.repository.db.places.PlacesDao
import com.fahmy.nearPlaces.repository.model.places.PlaceItem

/**
 * App Database
 * Define all entities and access doa's here/ Each entity is a table.
 */
@Database(entities = [PlaceItem::class], version = 4, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    /**
     * Get DAO
     */

    abstract fun placesDao(): PlacesDao

}