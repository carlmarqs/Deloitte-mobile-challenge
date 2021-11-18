package com.deloitte.wtest.ui.main

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deloitte.wtest.database.postalcode.PostalCode
import com.deloitte.wtest.database.postalcode.PostalCodeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class MainViewModel(private val databaseDao: PostalCodeDao) : ViewModel() {

    private val _postalCodes = MutableLiveData<List<PostalCode?>>()
    val postalCodes: LiveData<List<PostalCode?>>
        get() = _postalCodes

    private var getPostalCodeJob: Job? = null

    fun filterPostalCodes(filterOption: String? = null) {
        if (getPostalCodeJob?.isActive == true) {
            getPostalCodeJob?.cancel()
        }
        getPostalCodeJob = viewModelScope.launch(Dispatchers.IO) {
            if (filterOption == null) {
                _postalCodes.postValue(databaseDao.getAllPostalCodes())

            } else {
                val filterOptionT = filterOption?.lowercase()?.split(" ", "-")
                val filterOptionString = "%${filterOption?.lowercase()}%"
                _postalCodes.postValue(databaseDao.filterPostalCode(filterOptionT!!))
            }
        }


    }
}