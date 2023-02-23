package com.e4ekta.network_module.src.api

import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("android/carousell_news.json")
    suspend fun fetchCustomUI(): Response<ArrayList<CarousellNewsResponseItem>>
}