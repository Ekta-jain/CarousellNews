package com.e4ekta.network_module.src.api

import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getCarousellNewsList():Response<ArrayList<CarousellNewsResponseItem>>
}