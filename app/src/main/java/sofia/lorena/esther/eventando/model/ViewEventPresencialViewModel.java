package sofia.lorena.esther.eventando.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewEventPresencialViewModel extends AndroidViewModel {
    EventPresencial eventPresencial;

    public ViewEventPresencialViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<EventPresencial> getEventPresencialDetailsLD(String pid) {
        MutableLiveData<EventPresencial> eventPresencialDetailLD = new MutableLiveData<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            EventandoRepository eventandoRepository = new EventandoRepository(getApplication());
            EventPresencial eventPresencial = eventandoRepository.loadEventPresencialDetail(pid);

            // Atualize o status de favorito com base em lógica específica (consultando banco de dados local, etc.)
            // Aqui, assumimos que o status de favorito é obtido do servidor.
            eventPresencial.setFavorito(true); // Exemplo: Consideramos que é favorito

            eventPresencialDetailLD.postValue(eventPresencial);
        });

        return eventPresencialDetailLD;
    }

    public LiveData<Boolean> favoritar(String pid) {
        MutableLiveData<Boolean> favoritarEventLD = new MutableLiveData<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            EventandoRepository eventandoRepository = new EventandoRepository(getApplication());

            // Tente favoritar e obtenha o resultado
            Boolean favoritado = eventandoRepository.favorita(pid);

            if (favoritado) {
                // Se for favoritado, atualize o status localmente
                // Supondo que você tenha acesso ao objeto EventPresencial na sua classe ViewModel
                if (eventPresencial != null) {
                    eventPresencial.setFavorito(true);

                    // Salve no banco de dados local, se necessário
                    // eventandoRepository.salvarEventoLocal(eventPresencial);
                }
            }

            // Poste o resultado
            favoritarEventLD.postValue(favoritado);
        });

        return favoritarEventLD;
    }

    public LiveData<Boolean> desfavoritar(String pid) {
        MutableLiveData<Boolean> desfavoritarEventLD = new MutableLiveData<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            EventandoRepository eventandoRepository = new EventandoRepository(getApplication());

            // Tente desfavoritar e obtenha o resultado
            Boolean desfavoritado = eventandoRepository.desfavorita(pid);

            if (desfavoritado) {
                // Se for desfavoritado, atualize o status localmente
                // Supondo que você tenha acesso ao objeto EventPresencial na sua classe ViewModel
                if (eventPresencial != null) {
                    eventPresencial.setFavorito(false);

                    // Salve no banco de dados local, se necessário
                    // eventandoRepository.salvarEventoLocal(eventPresencial);
                }
            }

            // Poste o resultado
            desfavoritarEventLD.postValue(desfavoritado);
        });

        return desfavoritarEventLD;
    }
}
