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
import sofia.lorena.esther.eventando.R;

public class HomeViewModel extends AndroidViewModel {

    LiveData<PagingData<Event>> eventsLd;
    Integer selectedNavigationOpId = R.id.itemHome;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        EventandoRepository productsRepository = new EventandoRepository(getApplication());
        CoroutineScope viewModelScope = ViewModelKt.getViewModelScope(this);
        Pager<Integer, Event> pager = new Pager(new PagingConfig(10), () -> new EventsPagingSource(productsRepository));
        eventsLd = PagingLiveData.cachedIn(PagingLiveData.getLiveData(pager), viewModelScope);
    }

    public LiveData<PagingData<Event>> getEventsLiveData() {
        return eventsLd;
    }

    // Método para definir a opção de navegação selecionada
    public void setNavigationOpSelected(int navigationOpId) {
        selectedNavigationOpId = navigationOpId;
    }

    // Método para obter a opção de navegação selecionada
    public Integer getSelectedNavigationOpId() {

        return selectedNavigationOpId;
    }
}
