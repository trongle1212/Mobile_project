package com.example.comp1786cw2project2.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.comp1786cw2project2.feature.base.BaseViewModel
import com.example.comp1786cw2project2.local.database.UrlDao
import com.example.comp1786cw2project2.local.model.Url
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val urlDaoDatabase: UrlDao
) : BaseViewModel() {
    private var _currentUrl: MutableLiveData<Url> = MutableLiveData()
    var currentUrl : LiveData<Url> = _currentUrl
    private var currentIndex = 0

    fun storeImageToLocalDatabase(url: String) {
        urlDaoDatabase.addUrl(Url(url = url))
        val currentListUrl = urlDaoDatabase.getListUrl()
        currentIndex = currentListUrl.size - 1
        _currentUrl.value = Url(url = url)
    }

    fun getCurrentUrlImageFromDatabase(index: Int) {
        val listUrlImageFromDb = urlDaoDatabase.getListUrl()
        val range = listUrlImageFromDb.indices
        if (listUrlImageFromDb.isNotEmpty() && index in range) {
            _currentUrl.value = listUrlImageFromDb[index]
        }
    }

    fun onPreviousClicked() {
        val currentListUrl = urlDaoDatabase.getListUrl()
        val range = currentListUrl.indices
        currentIndex--
        if (currentListUrl.isNotEmpty() && currentIndex in range) {
            getCurrentUrlImageFromDatabase(currentIndex)
        } else {
            currentIndex++
        }
    }

    fun onNextClicked() {
        val currentListUrl = urlDaoDatabase.getListUrl()
        val range = currentListUrl.indices
        currentIndex++
        if (currentListUrl.isNotEmpty() && currentIndex in range) {
            getCurrentUrlImageFromDatabase(currentIndex)
        } else {
            currentIndex--
        }
    }
}