package com.e4ekta.network_module.src.api

import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : RemoteDataSource {

    override suspend fun getCarousellNewsList(): Response<ArrayList<CarousellNewsResponseItem>>  = apiService.getCarousellNewsList()

}