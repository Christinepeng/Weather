package com.weather.android

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes.CITIES
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.weather.android.databinding.ActivityMainBinding
import com.weather.android.ui.weather.WeatherActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var placesClient: PlacesClient
    private lateinit var currentLocationFinder: CurrentLocationFinder

    @SuppressLint("MissingPermission")
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                    // Only approximate location access granted.
                    currentLocationFinder.findCurrentPlaceWithPermissions()
                }

                else -> {
                    Toast.makeText(
                        this,
                        "Either ACCESS_COARSE_LOCATION or ACCESS_FINE_LOCATION permissions are required",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private fun hasOnePermissionGranted(permissions: Array<String>): Boolean =
        permissions.any {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // val apiKey = BuildConfig.PLACES_API_KEY
        val apiKey = "AIzaSyDQaPBODXTd9ZY6cW90ABbO0hn7FSh3oZs"

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        // Retrieve a PlacesClient (previously initialized - see MainActivity)
        placesClient = Places.createClient(this)

        currentLocationFinder = CurrentLocationFinder(
            requestPermissionLauncher,
            placesClient,
            binding,
            ::hasOnePermissionGranted
        )


        currentLocationFinder.findCurrentPlace()



        Log.e("xd", "start")

        val autocompleteSupportFragment =
            supportFragmentManager.findFragmentById(binding.autocompleteSupportFragment.id) as AutocompleteSupportFragment?
        // Reference: https://developers.google.com/maps/documentation/places/android-sdk/reference/com/google/android/libraries/places/api/model/PlaceTypes
        autocompleteSupportFragment!!.setTypesFilter(arrayListOf(CITIES))
        autocompleteSupportFragment!!.setPlaceFields(placeFields)
        autocompleteSupportFragment!!.setOnPlaceSelectedListener(placeSelectionListener)

        Log.e("xd", "end")
    }


    //    private val placeFields: List<Place.Field> get() = listOf(*Place.Field.values())
    private val placeFields: List<Place.Field> get() = listOf(Place.Field.NAME)

    private val placeSelectionListener: PlaceSelectionListener
        get() = object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
//                binding.response.text =
//                    StringUtil.stringifyAutocompleteWidget(place, isDisplayRawResultsChecked)
                Log.e("xd", place.toString())
                val intent = Intent(this@MainActivity, WeatherActivity::class.java)
                intent.putExtra("city_name", place.name)
                startActivity(intent)
            }

            override fun onError(status: Status) {
//                binding.response.text = status.statusMessage
                Log.e("xd", "Error on auto-complete: $status")
            }
        }

    override fun onSaveInstanceState(bundle: Bundle) {
        super.onSaveInstanceState(bundle)
    }


}

