package android.anda.vmk.ui.detail

import android.anda.vmk.data.AppRepository
import android.anda.vmk.data.model.MovieDetail
import android.anda.vmk.data.model.MovieItem
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class DetailViewModel(private val appRepository: AppRepository) : ViewModel() {

    val movieDetailLiveData: MutableLiveData<MovieDetail> = MutableLiveData()
    val messageLiveData: MutableLiveData<String> = MutableLiveData()

    fun loadData(id:String) {
        appRepository.getDetailMovie(id)
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
                }
                .toSingle()
                .subscribe(
                        { info ->
                            movieDetailLiveData.postValue(info as MovieDetail)
                        },
                        { error ->
                            messageLiveData.postValue("Failed open movie ${error!!.message}")
                        }
                )
    }

}