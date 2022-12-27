package com.example.attractions.presentation.mapfragment

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.example.attractions.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.attractions.databinding.MapFragmentBinding
import com.example.attractions.di.appComponent
import com.example.attractions.entity.Attractions
import com.example.attractions.entity.PreviewAttractions
import com.example.attractions.isPermissionsGranted
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.*
import javax.inject.Inject

class MapFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MapViewModelFactory
    private var _binding: MapFragmentBinding? = null
    private val binding get() = _binding!!
    private var locationListener: LocationSource.OnLocationChangedListener? = null
    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.isNotEmpty() && map.values.all { it }) {
                startLocation()
                kotlin.runCatching {
                    initializeGoogleMap()
                }
            }
        }
    private var mMap: GoogleMap? = null
    private lateinit var fusedLocation: FusedLocationProviderClient

    private val locationSource = object : LocationSource {
        override fun activate(p0: LocationSource.OnLocationChangedListener) {
            locationListener = p0
        }

        override fun deactivate() {
            locationListener = null
        }
    }
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            result.lastLocation?.let { location ->
                locationListener?.onLocationChanged(location)
                getAttractionList(location.latitude, location.longitude)
            }
        }
    }

    private lateinit var infoBottomSheet: BottomSheetBehavior<*>
    private val viewModel: MapViewModel by viewModels { viewModelFactory }

    private fun getAttractionList(lat: Double, lon: Double) {
        kotlin.runCatching {
            viewModel.getAttractionList(
                checkingSystemLanguage(),
                lon,
                lat
            )
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocation = LocationServices.getFusedLocationProviderClient(requireContext())
        requireContext().appComponent.injectInMapFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapFragmentBinding.inflate(layoutInflater)
        infoBottomSheet = BottomSheetBehavior.from(binding.bottom.bottomSheetInfo)
        viewModel.attractionsList.value?.let { addMarkers(it) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isPermissionsGranted(launcher, REQUEST_PERMISSION)) {
            mMap?.setLocationSource(locationSource)
            startLocation()
            kotlin.runCatching {
                initializeGoogleMap()
            }

        }
        observeOnAttractions()
    }

    override fun onStop() {
        fusedLocation.removeLocationUpdates(locationCallback)
        super.onStop()
    }

    override fun onDestroy() {
        locationListener = null
        _binding = null
        super.onDestroy()
    }

    private fun checkingSystemLanguage() =
        if (Locale.getDefault().toString() == "ru_RU") "ru" else "en"

    @SuppressLint("MissingPermission")
    private fun initializeGoogleMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            mMap?.let { map ->
                map.isMyLocationEnabled = true
                with(map.uiSettings) {
                    this.isZoomControlsEnabled = true
                    this.isMyLocationButtonEnabled = true
                }
                map.setPadding(0, 0, 0, 100)
                fusedLocation.lastLocation.addOnCompleteListener {
                    val lastLocationLatLng = LatLng(it.result.latitude, it.result.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLocationLatLng, 10f))
                }
            }
            observeOnListAttractions()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocation() {
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,600_000).build()
        fusedLocation.requestLocationUpdates(request, locationCallback, Looper.getMainLooper())
    }

    private fun addMarkers(previewAttractionsList: List<PreviewAttractions>?) {
        previewAttractionsList?.forEach { preview ->
            val latLngPreviewAttractions = LatLng(preview.point.lat, preview.point.lon)
            mMap?.addMarker(MarkerOptions().position(latLngPreviewAttractions))
        }
    }

    private fun onMarkerClickListener(previewAttractionsList: List<PreviewAttractions>?) {
        mMap?.setOnMarkerClickListener { marker ->
            val markerPosition = LatLng(marker.position.latitude, marker.position.longitude)
            previewAttractionsList?.forEach { item ->
                val itemPosition = LatLng(item.point.lat, item.point.lon)
                if (markerPosition == itemPosition) {
                    viewModel.getAttractionInfo(checkingSystemLanguage(), item.xid)
                }
            }
            showBottomSheet()
            return@setOnMarkerClickListener true
        }
    }

    private fun observeOnAttractions() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.attractions.collect { attractions ->
                attractions?.let {
                    setInfoInView(it)
                }
            }
        }
    }

    private fun showBottomSheet() {
        infoBottomSheet.setPeekHeight(120, true)
        infoBottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setInfoInView(attractions: Attractions) {
        binding.bottom.run {
            titleText.text = attractions.name
            Glide
                .with(requireContext())
                .load(attractions.image)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .into(pictureAttractions)
            descriptionText.text = attractions.info?.descr ?: attractions.wikipediaExtracts?.text
        }
    }

    private fun observeOnListAttractions() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.attractionsList.collect {
                addMarkers(it)
                onMarkerClickListener(it)
            }
        }
    }

    companion object {
        val REQUEST_PERMISSION = buildList {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }.toTypedArray()
    }
}