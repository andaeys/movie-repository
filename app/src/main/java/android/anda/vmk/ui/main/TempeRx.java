package android.anda.vmk.ui.main;

import android.anda.vmk.data.AppRepository;
import android.anda.vmk.data.model.MovieItem;
import android.anda.vmk.data.model.MovieListResponse;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Response;
import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;

public class TempeRx {
    private MutableLiveData<List<MovieItem>> liveData;
    private MutableLiveData<String> message;
    private AppRepository appRepository;

    public TempeRx(AppRepository appRepository) {
        this.appRepository = appRepository;
    }

    public void query(String title, int page){
        appRepository.searchItems(title, page)
                .flatMap(new Func1<Response<MovieListResponse>, Observable<List<MovieItem>>>() {
                    @Override
                    public Observable<List<MovieItem>> call(Response<MovieListResponse> response) {
                        if(!response.isSuccessful()){
                            return Observable.error(new Exception("response unsuccessful "+response.code()));
                        }
                        if(response.body()==null){
                            return Observable.error(new Exception("empty response "));
                        }
                        if(response.body().getMovieList().isEmpty()){
                            return Observable.error(new Exception("search result empty"));
                        }
                        return Observable.just(response.body().getMovieList());
                    }
                })
                .toSingle()
                .subscribe(new SingleSubscriber<List<MovieItem>>() {
                    @Override
                    public void onSuccess(List<MovieItem> movieItems) {

                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });

    }
}
