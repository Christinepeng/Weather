package com.weather.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.weather.android.logic.Repository
import com.weather.android.logic.model.Place

class PlaceViewModel : ViewModel() {
    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = searchLiveData.switchMap { query ->
        Repository.searchPlaces(query)
    }

    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }
}

// class UserViewModel: AndroidViewModel {
//    val nameQueryLiveData : MutableLiveData<String> = ...
//
//    fun usersWithNameLiveData(): LiveData<List<String>> = nameQueryLiveData.switchMap {
//        name -> myDataSource.usersWithNameLiveData(name)
//    }
//
//    fun setNameQuery(val name: String) {
//        this.nameQueryLiveData.value = name;
//    }
//}