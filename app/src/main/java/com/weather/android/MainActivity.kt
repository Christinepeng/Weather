package com.weather.android

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes.CITIES
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.weather.android.databinding.ActivityMainBinding
import com.weather.android.ui.weather.WeatherActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var placesClient: PlacesClient
    private lateinit var fieldSelector: FieldSelector

    @SuppressLint("MissingPermission")
    val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                    // Only approximate location access granted.
                    findCurrentPlaceWithPermissions()
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


        findCurrentPlace()



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
        fieldSelector.onSaveInstanceState(bundle)
    }

    /**
     * Check whether permissions have been granted or not, and ultimately proceeds to either
     * request them or runs {@link #findCurrentPlaceWithPermissions() findCurrentPlaceWithPermissions}
     */
    @SuppressLint("MissingPermission")
    private fun findCurrentPlace() {
        if (hasOnePermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)) {
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

//    var getCityNameFromAddress() {
//
//    }

    /**
     * Fetches a list of [com.google.android.libraries.places.api.model.PlaceLikelihood] instances that represent the Places the user is
     * most likely to be at currently.
     */
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE])
    private fun findCurrentPlaceWithPermissions() {
//        setLoading(true)
        val currentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeFields)
        val currentPlaceTask = placesClient.findCurrentPlace(currentPlaceRequest)
        currentPlaceTask.addOnSuccessListener { response: FindCurrentPlaceResponse? ->
            response?.let {
//                binding.response.text = StringUtil.stringify(it, isDisplayRawResultsChecked)
//                binding.title.text = StringUtil.stringify(it)
                binding.title.text = it.placeLikelihoods[0].place.address
                Log.e("xd", "Suc")
            }
        }
        currentPlaceTask.addOnFailureListener { exception: Exception ->
            exception.printStackTrace()
            Log.e("xd", "Failed $exception")
//            binding.response.text = exception.message
        }
//        currentPlaceTask.addOnCompleteListener { setLoading(false) }
    }
    private fun hasOnePermissionGranted(vararg permissions: String): Boolean =
        permissions.any {
            ContextCompat.checkSelfPermission(
                this,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
}