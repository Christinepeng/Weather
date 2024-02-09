package com.weather.android.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.weather.android.databinding.FragmentPlaceBinding
import com.weather.android.databinding.FragmentWeatherBinding
import com.weather.android.ui.place.PlaceAdapter
import com.weather.android.ui.place.PlaceViewModel

class WeatherFragment : Fragment() {

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }
    private lateinit var adapter: WeatherAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        adapter = WeatherAdapter(this, viewModel.weatherList)
        binding.recyclerView.adapter = adapter
//        binding.searchPlaceEdit.addTextChangedListener { editable ->
//            val content = editable.toString()
//            if (content.isNotEmpty()) {
//                viewModel.searchPlaces(content)
//            } else {
//                binding.recyclerView.visibility = View.GONE
//                binding.bgImageView.visibility = View.VISIBLE
//                viewModel.weatherList.clear()
//                adapter.notifyDataSetChanged()
//            }
//        }

        binding.searchBtn.setOnClickListener { _ ->
            val content = binding.searchPlaceEdit.text.toString()
            Log.v("xd", content)
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                binding.recyclerView.visibility = View.GONE
                binding.bgImageView.visibility = View.VISIBLE
                viewModel.weatherList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        viewModel.weatherLiveData.observe(viewLifecycleOwner, Observer { result ->
            val weather = result.getOrNull()
            Log.v("xd", "weather: ${weather.toString()}")
            if (weather != null) {
                binding.recyclerView.visibility = View.VISIBLE
                binding.bgImageView.visibility = View.GONE
                viewModel.weatherList.clear()
                viewModel.weatherList.addAll(weather)
                adapter.notifyDataSetChanged()
            } else {
//                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}