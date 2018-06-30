package android.anda.vmk.ui.main;

import android.anda.vmk.data.AppRepository;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class TempViewModel extends ViewModel {

    private AppRepository repository;

    private MutableLiveData<List<String>> listLiveData;

//    public LiveData<List<String>> getItemList(String query){
//        return repository.loadItems(query);
//    }
}
