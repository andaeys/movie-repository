package android.anda.vmk.utilities

import android.anda.vmk.data.AppRepository
import android.anda.vmk.data.api.ApiClient
import android.anda.vmk.ui.detail.DetailViewModelFactory
import android.anda.vmk.ui.main.HomeViewModelFactory

class InjectorUtil{
    companion object {
        private fun provideAppRepository() : AppRepository{
            val apiClient = ApiClient()
            return AppRepository(apiClient)

        }
        fun provideHomeViewModelFactory() : HomeViewModelFactory{
            val repository = provideAppRepository()
            return HomeViewModelFactory(repository)
        }

        fun provideDetailViewModelFactory() : DetailViewModelFactory{
            val repository = provideAppRepository()
            return DetailViewModelFactory(repository)
        }
    }
}