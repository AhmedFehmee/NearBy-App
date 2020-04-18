package com.fahmy.nearPlaces.repository.model.places

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "places_table")
data class PlaceItem(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("title") var title: String? = null,
    @SerializedName("category") var category: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("pic") var pic: String? = null
)