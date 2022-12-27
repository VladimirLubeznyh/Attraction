package com.example.attractions.domain

import com.example.attractions.data.Repository
import com.example.attractions.entity.Attractions
import javax.inject.Inject

class GetAttractionsInfoUseCase @Inject constructor(private val repository: Repository) {
    suspend fun execute(lang: String, xid: String): Attractions? {
        val data:Attractions? = try {
           repository.getInfoAttractions(lang = lang, xid = xid)
        }catch (e:Exception){
            null
        }
        return data
    }
}