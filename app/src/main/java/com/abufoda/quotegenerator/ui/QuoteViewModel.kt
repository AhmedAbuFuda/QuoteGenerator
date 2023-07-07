package com.abufoda.quotegenerator.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abufoda.quotegenerator.model.entity.MyDataItem
import com.abufoda.quotegenerator.model.local.Database
import com.abufoda.quotegenerator.model.local.LocalRepositoryImp
import com.abufoda.quotegenerator.model.remote.RemoteRepositoryImp
import com.abufoda.quotegenerator.model.remote.RetrofitBuilder
import com.abufoda.quotegenerator.model.remote.ServiceApi
import kotlinx.coroutines.launch
import retrofit2.create

class QuoteViewModel(application: Application): AndroidViewModel(application) {

    private var localRepositoryImp: LocalRepositoryImp
    private var remoteRepositoryImp: RemoteRepositoryImp

    private var quoteMutableLiveData = MutableLiveData<List<MyDataItem>>()
    val quoteLiveData : LiveData<List<MyDataItem>> get() = quoteMutableLiveData

    private var quoteApiMutableLiveData = MutableLiveData<List<MyDataItem>>()
    val quoteApiLiveData : LiveData<List<MyDataItem>> get() = quoteApiMutableLiveData

    init {
        val db = Database.getInstance(application)
        localRepositoryImp = LocalRepositoryImp(db)

        var serviceInstance = RetrofitBuilder.getRetrofitBuilder().create(ServiceApi::class.java)
        remoteRepositoryImp = RemoteRepositoryImp(serviceInstance)
    }


    fun getQuotesApi() =viewModelScope.launch {
       var result = remoteRepositoryImp.getData()
        if (result.isSuccessful){
            if (result.body() != null){
                quoteApiMutableLiveData.postValue(result.body())
            }
        }else{
            Log.i("TAG",result.message())
        }
    }

    fun getQuotes() =viewModelScope.launch {
        quoteMutableLiveData.postValue(localRepositoryImp.getAllQuote())
    }

    fun insertQuote(quote:MyDataItem){
        viewModelScope.launch {
            localRepositoryImp.insertQuote(quote)
        }
    }

    fun deleteQuote(quote: MyDataItem){
        viewModelScope.launch {
            localRepositoryImp.deleteQuote(quote)
        }
    }

    fun getCount(){
        viewModelScope.launch {
            localRepositoryImp.getCount()
        }
    }

}