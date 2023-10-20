package sofia.lorena.esther.eventando.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;

import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import kotlinx.coroutines.CoroutineScope;

/**
 * ViewModel referente a HomeActivity
 */
public class HomeViewModel extends AndroidViewModel {

    LiveData<PagingData<Event>> eventsLd;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        // Abaixo configuramos o uso da biblioteca de paginação Paging 3, assim como foi feito na
        // atividade Galeria Pública
        EventsRepository eventsRepository = new EventsRepository(getApplication());
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Event> pager = new Pager(new PagingConfig(10), () -> new EventsPagingSource(eventsRepository));
        eventsLd = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public LiveData<PagingData<Event>> getProductsLd() {
        return eventsLd;
    }

}
