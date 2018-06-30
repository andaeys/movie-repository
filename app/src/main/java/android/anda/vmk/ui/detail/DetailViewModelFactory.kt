package android.anda.vmk.ui.detail

import android.anda.vmk.data.AppRepository
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class DetailViewModelFactory(private val mRepository: AppRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(mRepository) as T
    }
}
