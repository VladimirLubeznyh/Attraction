package com.example.attractions.domain

import com.example.attractions.data.Repository
import com.example.attractions.domain.model.Attraction
import javax.inject.Inject

class GetAttractionsInfoUseCase @Inject constructor(private val repository: Repository) {

    suspend fun execute(xid: String): Attraction =
        repository.getInfoAttraction(xid = xid)
}
