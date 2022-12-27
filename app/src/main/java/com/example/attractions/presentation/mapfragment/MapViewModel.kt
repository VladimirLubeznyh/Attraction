package com.example.attractions.presentation.mapfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attractions.domain.GetAttractionsInfoUseCase
import com.example.attractions.domain.GetAttractionsListUseCase
import com.example.attractions.entity.Attractions
import com.example.attractions.entity.PreviewAttractions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private val getAttractionsList: GetAttractionsListUseCase,
    private val getAttractionsInfoUseCase: GetAttractionsInfoUseCase
) : ViewModel() {

    private val _attractionsList = MutableStateFlow<List<PreviewAttractions>?>(null)
    val attractionsList = _attractionsList.asStateFlow()

    private val _attractions = MutableStateFlow<Attractions?>(null)
    val attractions = _attractions.asStateFlow()

    fun getAttractionList(lang: String, lon: Double, lat: Double) {
        viewModelScope.launch {
            _attractionsList.value = getAttractionsList.execute(lang = lang, lon = lon, lat = lat)
        }
    }

    fun getAttractionInfo(lang: String, xid: String) {
        viewModelScope.launch {
            _attractions.value = getAttractionsInfoUseCase.execute(lang = lang, xid = xid)
        }
    }
}