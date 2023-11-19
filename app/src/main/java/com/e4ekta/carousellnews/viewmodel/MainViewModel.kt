package com.e4ekta.carousellnews.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.e4ekta.network_module.src.api.MainRepository
import com.e4ekta.network_module.src.model.CarousellNewsResponseItem
import com.e4ekta.network_module.src.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/*Main repository is created in Network Module*/
@HiltViewModel
class MainViewModel @Inject constructor(private val newListUseCase: NewListUseCase) :
    ViewModel() {


    private val filterBy = MutableStateFlow<FilterOptions>(FilterOptions.NONE)




    init {
       // fetchData()
    }

    fun sortBy(filterOptions: FilterOptions) {
        filterBy.update {
            filterOptions
        }
    }



    ////
//
//    private val _someData = MutableLiveData<SomeData>()
//    val someData: LiveData<SomeData> get() = _someData
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//    fun fetchData() {
//        viewModelScope.launch {
//            val result = useCase.getSomeData()
//            when (result) {
//                is ApiResponse.Success -> _someData.value = result.data
//                is ApiResponse.Error -> _error.value = result.message
//            }
//        }
//    }
}

enum class FilterOptions {
    POPULAR, RECENT, NONE
}