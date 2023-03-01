package com.e4ekta.carousellnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e4ekta.network_module.src.api.MainRepository
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import com.e4ekta.network_module.src.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    private val _myStateFlow =
        MutableStateFlow<Resource<ArrayList<CarousellNewsResponseItem>>>(Resource.loading())
    val myStateFlow: StateFlow<Resource<ArrayList<CarousellNewsResponseItem>>> get() = _myStateFlow

    init {
        fetchData()
    }

     fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            flow<ArrayList<CarousellNewsResponseItem>> {
                val usersFromApi = mainRepository.fetchCustomUI().data

                emit(usersFromApi!!)
            }.catch { exception ->
                // _myStateFlow.value = exception.message
            }.collect { data ->
                _myStateFlow.value = Resource.success(data)
            }
        }
    }
}