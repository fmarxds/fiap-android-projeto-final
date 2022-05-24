package br.com.fiap42scj.crudandroid.presentation.carlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap42scj.crudandroid.data.db.CarsEntity
import br.com.fiap42scj.crudandroid.repository.CarsRepository
import br.com.fiap42scj.crudandroid.utils.FirebaseUtils
import kotlinx.coroutines.launch

class CarListViewModel(private val repository: CarsRepository) : ViewModel() {

    private val _allCarsEvent = MutableLiveData<List<CarsEntity>>()
    val allCarsEvent: LiveData<List<CarsEntity>>get() = _allCarsEvent
    private var firebaseConnection: FirebaseUtils = FirebaseUtils()

    fun getCars() = viewModelScope.launch {
        _allCarsEvent.postValue(repository.getAllCars())
        firebaseConnection.readValues()
    }
}