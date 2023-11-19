package com.e4ekta.carousellnews.viewmodel

import androidx.lifecycle.viewModelScope
import com.e4ekta.network_module.src.api.MainRepository
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import com.e4ekta.network_module.src.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewListUseCase @Inject constructor(private val mainRepository: MainRepository) {

    private val _myStateFlow =
        MutableStateFlow<Resource<ArrayList<CarousellNewsResponseItem>>>(Resource.loading())


    suspend operator fun invoke(query: String?): Result<List<CarousellNewsResponseItem>> {
        /*val result = albumRepository
            .searchAlbum(query)
            .mapSuccess {
                val albumsWithImages = value.filter { it.getDefaultImageUrl() != null }

                copy(value = albumsWithImages)
            }

        return result*/
    }

    val uiState: Flow<Resource<ArrayList<CarousellNewsResponseItem>>> =
        combine(flow = filterBy, flow2 = _myStateFlow) { filterOptions, resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    val list = ArrayList<CarousellNewsResponseItem>()
                    resource.data?.let { list.addAll(it) }
                    when (filterOptions) {
                        FilterOptions.POPULAR -> {
                            list.sortBy { it.rank }
                        }
                        FilterOptions.RECENT -> {
                            list.sortBy { it.timeCreated }
                        }
                        FilterOptions.NONE -> {

                        }
                    }
                    Resource.success(list)
                }

                Resource.Status.ERROR -> resource
                Resource.Status.LOADING -> resource
            }
        }

    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            _myStateFlow.update {
                mainRepository.getCarousellNewsList()
            }
        }
    }



}