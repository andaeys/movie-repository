package android.anda.vmk.ui.detail

import android.anda.vmk.data.AppRepository
import android.anda.vmk.data.model.MovieDetail
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DetailViewModel(private val appRepository: AppRepository) : ViewModel() {

    val movieDetailLiveData: MutableLiveData<MovieDetail> = MutableLiveData()
    val errorLiveData: MutableLiveData<String> = MutableLiveData()
    private lateinit var subscription : Subscription

    fun loadData(id:String) {
        subscription = appRepository.getDetailMovie(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { response ->
                if (!response.isSuccessful) {
                    Observable.error<String>(Exception("response unsuccessful ${response.code()}"))
                } else {
                    if (response.body()==null) {
                        Observable.error<String>(Exception("body empty"))
                    } else {
                        Observable.just(response.body())
                    }
                }
            }.toSingle()
            .subscribe(
                { info -> movieDetailLiveData.postValue(info as MovieDetail) },
                { error -> errorLiveData.postValue("Failed to open the movie ${error!!.message}")}
            )
    }

    override fun onCleared() {
        super.onCleared()
        if(!subscription!!.isUnsubscribed)subscription!!.unsubscribe()
    }

}