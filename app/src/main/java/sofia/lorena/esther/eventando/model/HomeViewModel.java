package sofia.lorena.esther.eventando.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelKt;
import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.PagingLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kotlinx.coroutines.CoroutineScope;

import sofia.lorena.esther.eventando.R;

public class HomeViewModel extends AndroidViewModel {

    LiveData<PagingData<Event>> eventsLd;

    LiveData<PagingData<Event>> myEventsLd;
    Integer selectedNavigationOpId = R.id.itemHome;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        EventandoRepository eventandoRepository = new EventandoRepository(getApplication());
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);

        Pager<Integer, Event> pager = new Pager(new PagingConfig(10), () -> new EventsPagingSource(eventandoRepository));
        eventsLd = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);

        Pager<Integer, Event> pager1 = new Pager(new PagingConfig(10), () -> new MyEventsPagingSource(eventandoRepository));
        myEventsLd = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager1), viewModelScope);
    }

    public LiveData<PagingData<Event>> getEventsLiveData() {
        return eventsLd;
    }

    public LiveData<PagingData<Event>> getMyEventsLiveData() {
        return myEventsLd;
    }

    public LiveData<List<Event>> getEventsDoMomento() {
        MutableLiveData<List<Event>> result = new MutableLiveData<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                EventandoRepository eventandoRepository = new EventandoRepository(getApplication());
                List<Event> eventsDoMomento = eventandoRepository.loadEventsDoMomento();
                result.postValue(eventsDoMomento);
            }
        });
        return result;
    }

    public LiveData<List<Event>> getEventPesquisaLD(String pesquisa) {
        MutableLiveData<List<Event>> eventPesquisaLD = new MutableLiveData<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                EventandoRepository eventandoRepository = new EventandoRepository(getApplication());
                List<Event> p = eventandoRepository.searchEvents(pesquisa);
                eventPesquisaLD.postValue(p);
            }
        });
        return eventPesquisaLD;
    }

    public void setNavigationOpSelected(int navigationOpId) {
        selectedNavigationOpId = navigationOpId;
    }

    public Integer getSelectedNavigationOpId() {
        return selectedNavigationOpId;
    }
}
