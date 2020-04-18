package com.fahmy.nearPlaces.ui.places

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.fahmy.nearPlaces.R
import com.fahmy.nearPlaces.repository.model.places.PlaceItem
import com.fahmy.nearPlaces.utils.extensions.inflate
import kotlinx.android.synthetic.main.row_place.view.*

class PlacesAdapter(private val listener: (PlaceItem) -> Unit) : RecyclerView.Adapter<PlacesAdapter.PlacesHolder>() {

    private var places: List<PlaceItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlacesHolder(parent.inflate(R.layout.row_place))

    override fun onBindViewHolder(placesHolder: PlacesHolder, position: Int) =
        placesHolder.bind(places[position], listener)

    override fun getItemCount() = places.size

    /**
     * View Holder Pattern
     */
    class PlacesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(place: PlaceItem, listener: (PlaceItem) -> Unit) = with(itemView) {
            tv_place_category.text = place.category
            tv_place_title.text = place.title
            tv_place_address.text = place.address

            Glide.with(context)
                .load(place.pic)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.image_icon)
                        .error(R.drawable.image_icon)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(iv_place_Image)

            setOnClickListener { listener(place) }

        }
    }

    fun replaceItems(items: List<PlaceItem>) {
        places = items
        notifyDataSetChanged()
    }
}