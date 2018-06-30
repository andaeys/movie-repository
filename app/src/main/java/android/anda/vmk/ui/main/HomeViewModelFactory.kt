package android.anda.vmk.ui.main

import android.anda.vmk.data.AppRepository
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class HomeViewModelFactory(private val mRepository: AppRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(mRepository) as T
    }
}
