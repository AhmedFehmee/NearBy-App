package com.fahmy.nearPlaces.repository.model.places

import com.google.gson.annotations.SerializedName

class Place {
    @SerializedName("response")
    var response: ResponseEntity? = null

    @SerializedName("meta")
    var meta: MetaEntity? = null

    class ResponseEntity {
        @SerializedName("groups")
        var groups: List<GroupsEntity>? = null

        @SerializedName("suggestedBounds")
        var suggestedBounds: SuggestedBoundsEntity? = null

        @SerializedName("totalResults")
        var totalResults = 0

        @SerializedName("headerLocationGranularity")
        var headerLocationGranularity: String? = null

        @SerializedName("headerFullLocation")
        var headerFullLocation: String? = null

        @SerializedName("headerLocation")
        var headerLocation: String? = null

        @SerializedName("suggestedRadius")
        var suggestedRadius = 0

        @SerializedName("suggestedFilters")
        var suggestedFilters: SuggestedFiltersEntity? = null
    }

    class GroupsEntity {
        @SerializedName("items")
        var items: List<ItemsEntity>? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("type")
        var type: String? = null
    }

    class ItemsEntity {
        @SerializedName("referralId")
        var referralId: String? = null

        @SerializedName("venue")
        var venue: VenueEntity? = null

        @SerializedName("reasons")
        var reasons: ReasonsEntity? = null
    }

    class VenueEntity {
        @SerializedName("photos")
        var photos: PhotosEntity? = null

        @SerializedName("categories")
        var categories: List<CategoriesEntity>? = null

        @SerializedName("location")
        var location: LocationEntity? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("id")
        var id: String? = null
    }

    class PhotosEntity {
        @SerializedName("groups")
        var groups: List<String>? = null

        @SerializedName("count")
        var count = 0
    }

    class CategoriesEntity {
        @SerializedName("primary")
        var primary = false

        @SerializedName("icon")
        var icon: IconEntity? = null

        @SerializedName("shortName")
        var shortName: String? = null

        @SerializedName("pluralName")
        var pluralName: String? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("id")
        var id: String? = null
    }

    class IconEntity {
        @SerializedName("suffix")
        var suffix: String? = null

        @SerializedName("prefix")
        var prefix: String? = null
    }

    class LocationEntity {
        @SerializedName("formattedAddress")
        var formattedAddress: List<String>? = null

        @SerializedName("country")
        var country: String? = null

        @SerializedName("state")
        var state: String? = null

        @SerializedName("city")
        var city: String? = null

        @SerializedName("cc")
        var cc: String? = null

        @SerializedName("distance")
        var distance = 0

        @SerializedName("labeledLatLngs")
        var labeledLatLngs: List<LabeledLatLngsEntity>? = null

        @SerializedName("lng")
        var lng = 0.0

        @SerializedName("lat")
        var lat = 0.0

        @SerializedName("address")
        var address: String? = null
    }

    class LabeledLatLngsEntity {
        @SerializedName("lng")
        var lng = 0.0

        @SerializedName("lat")
        var lat = 0.0

        @SerializedName("label")
        var label: String? = null
    }

    class ReasonsEntity {
        @SerializedName("items")
        var items: List<ReasonsItemsEntity>? = null

        @SerializedName("count")
        var count = 0
    }

    class ReasonsItemsEntity {
        @SerializedName("reasonName")
        var reasonName: String? = null

        @SerializedName("type")
        var type: String? = null

        @SerializedName("summary")
        var summary: String? = null
    }

    class SuggestedBoundsEntity {
        @SerializedName("sw")
        var sw: SwEntity? = null

        @SerializedName("ne")
        var ne: NeEntity? = null
    }

    class SwEntity {
        @SerializedName("lng")
        var lng = 0.0

        @SerializedName("lat")
        var lat = 0.0
    }

    class NeEntity {
        @SerializedName("lng")
        var lng = 0.0

        @SerializedName("lat")
        var lat = 0.0
    }

    class SuggestedFiltersEntity {
        @SerializedName("filters")
        var filters: List<FiltersEntity>? = null

        @SerializedName("header")
        var header: String? = null
    }

    class FiltersEntity {
        @SerializedName("key")
        var key: String? = null

        @SerializedName("name")
        var name: String? = null
    }

    class MetaEntity {
        @SerializedName("requestId")
        var requestId: String? = null

        @SerializedName("code")
        var code = 0
    }
}