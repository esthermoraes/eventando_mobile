package sofia.lorena.esther.eventando.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import sofia.lorena.esther.eventando.R;

public class CriarEventViewModel extends AndroidViewModel {

    Integer selectedEventType = R.id.btnPresencial;

    String currentPhotoPath = "";

    public CriarEventViewModel(@NonNull Application application) {
        super(application);
    }

    public Integer getSelectedEventType() {
        return selectedEventType;
    }

    public void setSelectedEventType(Integer selectedEventType) {
        this.selectedEventType = selectedEventType;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    public void setCurrentPhotoPath(String currentPhotoPath) {
        this.currentPhotoPath = currentPhotoPath;
    }

    public LiveData<Boolean> criarEventoOnline(String nome, String objetivo, String data, String hora, String imagem, String link_evento, int plataforma_evento, String privacidade) {

        // Cria um container do tipo MutableLiveData (um LiveData que pode ter seu conteúdo alterado).
        MutableLiveData<Boolean> result = new MutableLiveData<>();

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

                // O método addProduct envia os dados de um novo produto ao servidor. Ele retorna
                // um booleano indicando true caso o produto tenha sido cadastrado e false
                // em caso contrário
                boolean b = eventandoRepository.criarEventoOnline(nome, objetivo, data, hora, imagem, link_evento, plataforma_evento, privacidade);

                // Aqui postamos o resultado da operação dentro do LiveData. Quando fazemos isso,
                // quem estiver observando o LiveData será avisado de que o resultado está disponível.
                result.postValue(b);
            }
        });

        return result;
    }

    public LiveData<Boolean> criarEventoPresencial(String nome, String objetivo, String data, String hora, String imagem, String numero, int tipo_logradouro, String bairro_evento, String cidade_evento, int estado_evento, int cep, String privacidade) {

        // Cria um container do tipo MutableLiveData (um LiveData que pode ter seu conteúdo alterado).
        MutableLiveData<Boolean> result = new MutableLiveData<>();

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

                // O método addProduct envia os dados de um novo produto ao servidor. Ele retorna
                // um booleano indicando true caso o produto tenha sido cadastrado e false
                // em caso contrário
                boolean b = eventandoRepository.criarEventoPresencial(nome, objetivo, data, hora, imagem, numero, tipo_logradouro, bairro_evento, cidade_evento, estado_evento, cep, privacidade);

                // Aqui postamos o resultado da operação dentro do LiveData. Quando fazemos isso,
                // quem estiver observando o LiveData será avisado de que o resultado está disponível.
                result.postValue(b);
            }
        });

        return result;
    }
}
