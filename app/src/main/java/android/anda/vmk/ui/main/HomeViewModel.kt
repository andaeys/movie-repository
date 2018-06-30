package android.anda.vmk.ui.main

import android.anda.vmk.data.AppRepository
import android.anda.vmk.data.model.MovieItem
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class HomeViewModel(private val appRepository: AppRepository) : ViewModel() {

    val listItemLiveData: MutableLiveData<List<MovieItem>> = MutableLiveData()
    val messageLiveData: MutableLiveData<String> = MutableLiveData()

    fun loadData(title: String, page: Int) {
        appRepository.searchItems(title, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { response ->
                    if (!response.isSuccessful) {
                        Observable.error<String>(Exception("response unsuccessful ${response.code()}"))
                    } else {
                        if (response.body().movieList!!.isEmpty()) {
                            Observable.error<String>(Exception("list empty"))
                        } else {
                            Observable.just(response.body().movieList)
                        }
                    }
                }
                .toSingle()
                .subscribe(
                        { list ->
                            listItemLiveData.postValue(list as List<MovieItem>)
                        },
                        { error ->
                            messageLiveData.postValue("Failed search movies ${error!!.message}")
                        }
                )
    }

}