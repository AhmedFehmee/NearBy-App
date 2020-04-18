package com.fahmy.nearPlaces.utils.permissions

import android.Manifest
import android.app.Activity
import com.fahmy.nearPlaces.utils.gps.LocationHelper
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable

class RequestPermissions {

    companion object {

        fun requirePermission(context: Activity): Boolean {

            val permissions = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE
            )
            for (permission in permissions) {
                val granted = RxPermissions(context).isGranted(permission)
                if (!granted)
                    return false
            }
            return true
        }

        fun askForPermissions(activity: Activity) {
            CompositeDisposable().add(
                RxPermissions(activity).request(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).subscribe {
                    if (it)
                        if (!LocationHelper.isLocationEnabled(activity)) {
                            LocationHelper.displayLocationSettingsRequest(activity)
                        }
                })
        }
    }
}