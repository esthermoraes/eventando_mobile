package sofia.lorena.esther.eventando.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagingData;

import sofia.lorena.esther.eventando.R;

public class HomeViewModel extends AndroidViewModel {

    LiveData<PagingData<Event>> eventsLd;
    MutableLiveData<Integer> selectedNavigationOpId = new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);

        // Configuração da biblioteca de paginação (Paging 3) ...

        // Defina o valor padrão para a opção de navegação selecionada
        selectedNavigationOpId.setValue(R.id.itemPerfil); // Valor padrão para a opção de perfil

        // Resto da configuração do ViewModel ...
    }

    public LiveData<PagingData<Event>> getEventsLiveData() {
        return eventsLd;
    }

    // Método para definir a opção de navegação selecionada
    public void setNavigationOpSelected(int navigationOpId) {
        selectedNavigationOpId.setValue(navigationOpId);
    }

    // Método para obter a opção de navegação selecionada
    public LiveData<Integer> getSelectedNavigationOpId() {
        return selectedNavigationOpId;
    }
}
