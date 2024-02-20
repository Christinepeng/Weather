package com.weather.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes.CITIES
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.weather.android.databinding.ActivityMainBinding
import com.weather.android.ui.weather.WeatherActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val apiKey = BuildConfig.PLACES_API_KEY
        val apiKey = "AIzaSyDQaPBODXTd9ZY6cW90ABbO0hn7FSh3oZs"

        // Setup Places Client
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        Log.e("xd", "start")

        val autocompleteSupportFragment =
            supportFragmentManager.findFragmentById(binding.autocompleteSupportFragment.id) as AutocompleteSupportFragment?
        // Reference: https://developers.google.com/maps/documentation/places/android-sdk/reference/com/google/android/libraries/places/api/model/PlaceTypes
        autocompleteSupportFragment!!.setTypesFilter(arrayListOf(CITIES))
        autocompleteSupportFragment!!.setPlaceFields(placeFields)
        autocompleteSupportFragment!!.setOnPlaceSelectedListener(placeSelectionListener)

        Log.e("xd", "end")
    }

    private val placeFields: List<Place.Field> get() = listOf(*Place.Field.values())
//    private val placeFields: List<Place.Field> get() = listOf(Place.Field.NAME)

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
}