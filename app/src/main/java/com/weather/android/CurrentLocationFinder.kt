package com.weather.android

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresPermission
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.weather.android.databinding.ActivityMainBinding

class CurrentLocationFinder(
    private val requestPermissionLauncher: ActivityResultLauncher<Array<String>>,
    private val placesClient: PlacesClient,
    private val binding: ActivityMainBinding,
    private val hasOnePermissionGranted: (permissions: Array<String>) -> Boolean,
) {

    /**
     * Check whether permissions have been granted or not, and ultimately proceeds to either
     * request them or runs {@link #findCurrentPlaceWithPermissions() findCurrentPlaceWithPermissions}
     */
    @SuppressLint("MissingPermission")
    fun findCurrentPlace() {
        if (hasOnePermissionGranted(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        ) {
            findCurrentPlaceWithPermissions()
            return
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    /**
     * Fetches a list of [com.google.android.libraries.places.api.model.PlaceLikelihood] instances that represent the Places the user is
     * most likely to be at currently.
     */
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE])
    fun findCurrentPlaceWithPermissions() {
//        setLoading(true)
        val currentPlaceRequest = FindCurrentPlaceRequest.newInstance(listOf(Place.Field.ADDRESS))
        val currentPlaceTask = placesClient.findCurrentPlace(currentPlaceRequest)
        currentPlaceTask.addOnSuccessListener { response: FindCurrentPlaceResponse? ->
            response?.let {
//                binding.response.text = StringUtil.stringify(it, isDisplayRawResultsChecked)
//                binding.title.text = StringUtil.stringify(it)

                binding.title.text =
                    getCityNameFromAddress(it.placeLikelihoods[0].place.address!!) + " (Current Location)"
//                Log.e("xd", "Suc ${StringUtil.stringify(it)}")
            }
        }
        currentPlaceTask.addOnFailureListener { exception: Exception ->
            exception.printStackTrace()
            Log.e("xd", "Failed $exception")
//            binding.response.text = exception.message
        }
//        currentPlaceTask.addOnCompleteListener { setLoading(false) }
    }

    private fun getCityNameFromAddress(placeAddress: String): String {
        val placeAddressSplitByComma = placeAddress.split(",").reversed()
        return placeAddressSplitByComma[2]
    }
}