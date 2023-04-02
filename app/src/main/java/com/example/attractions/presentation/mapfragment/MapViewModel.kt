package com.example.attractions.presentation.mapfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attractions.domain.GetAttractionsInfoUseCase
import com.example.attractions.domain.GetAttractionsPreviewListUseCase
import com.example.attractions.data.network.response.ResponseAttraction
import com.example.attractions.data.network.response.ResponsePreviewAttraction
import com.example.attractions.domain.model.Attraction
import com.example.attractions.domain.model.PreviewAttraction
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val getAttractionsList: GetAttractionsPreviewListUseCase,
    private val getAttractionsInfoUseCase: GetAttractionsInfoUseCase
) : ViewModel() {

    private val _attractionsList = MutableStateFlow<List<PreviewAttraction>>(emptyList())
    val attractionsList = _attractionsList.asStateFlow()

    private val _attractions = MutableStateFlow<Attraction?>(null)
    val attractions = _attractions.asStateFlow()
    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> }
    fun getAttractionList(lon: Double, lat: Double) {
        viewModelScope.launch(exceptionHandler) {
            _attractionsList.value = getAttractionsList.execute(lon = lon, lat = lat)
        }
    }

    fun getAttractionInfo(position: LatLng) {
        viewModelScope.launch(exceptionHandler) {
            attractionsList.value.forEach {
                val itemPosition = LatLng(it.lat, it.lon)
                if (position == itemPosition) {
                    _attractions.value = getAttractionsInfoUseCase.execute(xid = it.id)
                }
            }
        }
    }
}
