package sofia.lorena.esther.eventando.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagingData;

import sofia.lorena.esther.eventando.R;

public class HomeViewModel extends AndroidViewModel {

    LiveData<PagingData<Event>> eventsLd;
    Integer selectedNavigationOpId = R.id.itemHome;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        // Configuração da biblioteca de paginação (Paging 3) ...

        // Defina o valor padrão para a opção de navegação selecionada

        // Resto da configuração do ViewModel ...
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
