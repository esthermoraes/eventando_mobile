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

        // Cria um container do tipo MutableLiveData (um LiveData que pode ter seu conteúdo alterado).
        MutableLiveData<List<Event>> result = new MutableLiveData<>();

        // Cria uma nova linha de execução (thread). O android obriga que chamadas de rede sejam feitas
        // em uma linha de execução separada da principal.
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Executa a nova linha de execução. Dentro dessa linha, iremos realizar as requisições ao
        // servidor web.
        executorService.execute(new Runnable() {

            /**
             * Tudo o que colocármos dentro da função run abaixo será executada dentro da nova linha
             * de execução.
             */
            @Override
            public void run() {

                // Criamos uma instância de ProductsRepository. É dentro dessa classe que estão os
                // métodos que se comunicam com o servidor web.
                EventandoRepository eventandoRepository = new EventandoRepository(getApplication());

                // O método login envia os dados de novo usuário ao servidor. Ele retorna
                // um booleano indicando true caso o cadastro de novo usuário tenha sido feito com sucesso e false
                // em caso contrário
                List<Event> eventsDoMomento = eventandoRepository.loadEventsDoMomento();

                // Aqui postamos o resultado da operação dentro do LiveData. Quando fazemos isso,
                // quem estiver observando o LiveData será avisado de que o resultado está disponível.
                result.postValue(eventsDoMomento);
            }
        });

        return result;
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
