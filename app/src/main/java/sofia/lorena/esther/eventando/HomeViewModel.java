package sofia.lorena.esther.eventando;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import com.example.produtos.util.HttpRequest;
import com.example.produtos.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlinx.coroutines.CoroutineScope;

/**
 * ViewModel referente a HomeActivity
 */
public class HomeViewModel extends AndroidViewModel {

    LiveData<PagingData<Events>> eventsLd;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        // Abaixo configuramos o uso da biblioteca de paginação Paging 3, assim como foi feito na
        // atividade Galeria Pública
        EventsRepository productsRepository = new EventsRepository(getApplication());
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Product> pager = new Pager(new PagingConfig(10), () -> new ProductsPagingSource(EventsRepository));
        eventsLd = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public LiveData<PagingData<Events>> getProductsLd() {
        return eventsLd;
    }

}
