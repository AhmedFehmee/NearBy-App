package com.fahmy.nearPlaces.ui.places

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.GravityEnum
import com.afollestad.materialdialogs.MaterialDialog
import com.fahmy.nearPlaces.R
import com.fahmy.nearPlaces.ui.BaseActivity
import com.fahmy.nearPlaces.utils.*
import com.fahmy.nearPlaces.utils.extensions.getViewModel
import com.fahmy.nearPlaces.utils.extensions.load
import com.fahmy.nearPlaces.utils.extensions.observe
import com.fahmy.nearPlaces.utils.extensions.toast
import com.fahmy.nearPlaces.utils.gps.LocationHelper
import com.fahmy.nearPlaces.utils.gps.LocationTracker
import com.fahmy.nearPlaces.utils.permissions.RequestPermissions
import kotlinx.android.synthetic.main.activity_places.*
import kotlinx.android.synthetic.main.view_empty_data.*
import kotlinx.android.synthetic.main.view_loading_progress.*
import kotlinx.android.synthetic.main.view_something_wrong.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PlacesActivity : BaseActivity() {

    private lateinit var adapter: PlacesAdapter
    private var modeStatus: Boolean = true

    private val placeViewModel by lazy { getViewModel<PlacesViewModel>() }

    /**
     * On Create Of Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)

        refresh.setColorSchemeResources(R.color.colorPrimary)

        news_list.setEmptyView(empty_view)
        news_list.setProgressView(progress_view)
        news_list.setWrongView(wrong_view)

        adapter = PlacesAdapter {
            toast(it.title.toString())
        }
        news_list.adapter = adapter
        news_list.layoutManager = LinearLayoutManager(this)

        checkSensorAvailability()

        handleClicks()
    }

    private fun handleClicks() {
        btn_mode.setOnClickListener {
            modeStatus = !modeStatus
            btn_mode.text = if (modeStatus) getString(R.string.realtime_mode) else getString(R.string.single_mode)
        }

        refresh.setOnRefreshListener {
            refresh.isRefreshing = false
            checkSensorAvailability()
        }
    }

    /**
     * Get Places using Network & DB Bound Resource
     */
    private fun getPlaces(gpsLat: Double?, gpsLon: Double?) {
        refresh.isRefreshing = true

        /*
        * Observing for data change, Cater DB and Network Both
        * */
        placeViewModel.getPlaces(gpsLat, gpsLon).observe(this) {
            when {
                it.status.isLoading() -> {
                    news_list.showProgressView()
                }
                it.status.isSuccessful() -> {
                    it.load(news_list) {
                        // Update the UI as the data has changed
                        it?.let { adapter.replaceItems(it) }
                    }
                }
                it.status.isError() -> {
                    news_list.showWrongView()
                }
            }
            refresh.isRefreshing = false
        }
    }


    /**
     * Check sensors
     */
    private fun checkSensorAvailability() {
        if (!ConnectivityUtil.isConnected(this)) {
            MaterialDialog.Builder(this).title(getString(R.string.whoops)).content(getString(R.string.no_internet_connection))
                .positiveText(getString(R.string.connect_to_internet)).negativeText(getString(R.string.cancel)).cancelable(true)
                .contentGravity(GravityEnum.CENTER).btnStackedGravity(GravityEnum.CENTER).itemsGravity(GravityEnum.CENTER)
                .onPositive { _, _ -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS)) }
                .onNegative { _, _ -> }.iconRes(R.drawable.no_cloud_icon).maxIconSize(95)
                .show()
        } else if (!RequestPermissions.requirePermission(this)) {
            RequestPermissions.askForPermissions(this)
        } else if (!LocationHelper.isLocationEnabled(this)) {
            LocationHelper.displayLocationSettingsRequest(this)
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        // create class object
        val gpsTracker = LocationTracker(this)
        gpsTracker.getCurrentLocation()

        if (LocationHelper.isLocationEnabled(this)) {
            // check if GPS enabled
            if (gpsTracker.canGetLocation()) {

                val gpsLat = gpsTracker.getDeviceLatitude()
                val gpsLon = gpsTracker.getDeviceLongitude()
                getPlaces(gpsLat, gpsLon)
            }
        }
    }


    /**
     * Subscribe of EventBus to get the changes of Location
     * and Fetch the changes depending on Mode status (Realtime / Single)
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLocationChanged(event: LocationTracker.LocationChangeListener?) {
        if (modeStatus)
            getPlaces(event?.location?.latitude, event?.location?.longitude)
    }

    /**
     * On Destroy Of Activity
     */
    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    /**
     * On Start Of Activity
     */
    override fun onStart() {
        try {
            EventBus.getDefault().register(this)
        } catch (e: Exception) {
            //ignored
        }
        super.onStart()
    }
}