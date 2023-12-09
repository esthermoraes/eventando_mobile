package sofia.lorena.esther.eventando.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViewEventOnlineViewModel extends AndroidViewModel {
    EventOnline eventOnline;

    public ViewEventOnlineViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<EventOnline> getEventOnlineDetailsLD(String pid) {
        MutableLiveData<EventOnline> eventOnlineDetailLD = new MutableLiveData<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            EventandoRepository eventandoRepository = new EventandoRepository(getApplication());
            EventOnline eventOnline = eventandoRepository.loadEventOnlineDetail(pid);

            // Atualize o status de favorito com base em lógica específica (consultando banco de dados local, etc.)
            // Aqui, assumimos que o status de favorito é obtido do servidor.
            eventOnline.setFavorito(true); // Exemplo: Consideramos que é favorito

            eventOnlineDetailLD.postValue(eventOnline);
        });

        return eventOnlineDetailLD;
    }

    public LiveData<Boolean> favoritar(String pid) {
        MutableLiveData<Boolean> favoritarEventLD = new MutableLiveData<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(() -> {
            EventandoRepository eventandoRepository = new EventandoRepository(getApplication());

            // Verifique se o evento já foi favoritado antes de tentar favoritar novamente
            if (eventOnline == null || !eventOnline.isFavorito()) {
                // Tente favoritar e obtenha o resultado
                Boolean favoritado = eventandoRepository.favorita(pid);

                if (favoritado) {
                    // Se for favoritado, atualize o status localmente
                    if (eventOnline != null) {
                        eventOnline.setFavorito(true);
                    }
                }

                // Poste o resultado
                favoritarEventLD.postValue(favoritado);
            } else {
                // Se o evento já foi favoritado, informe que não foi possível favoritar novamente
                favoritarEventLD.postValue(false);
            }
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
                // Supondo que você tenha acesso ao objeto EventOnline na sua classe ViewModel
                if (eventOnline != null) {
                    eventOnline.setFavorito(false);

                    // Salve no banco de dados local, se necessário
                    // eventandoRepository.salvarEventoLocal(eventOnline);
                }
            }

            // Poste o resultado
            desfavoritarEventLD.postValue(desfavoritado);
        });

        return desfavoritarEventLD;
    }
}
