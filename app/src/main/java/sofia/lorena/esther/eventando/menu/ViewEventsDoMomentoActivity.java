package sofia.lorena.esther.eventando.menu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.model.Event;
import sofia.lorena.esther.eventando.model.ViewEventViewModel;
import sofia.lorena.esther.eventando.util.ImageCache;

public class ViewEventsDoMomentoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_carousel_item);

        // Para obter os detalhes do produto, a app envia o id do produto ao servidor web. Este
        // último responde com os detalhes do produto referente ao pid.

        // O pid do produto é enviado para esta tela quando o produto é clicado na tela de Home.
        // Aqui nós obtemos o pid.
        Intent i = getIntent();
        String pid = i.getStringExtra("pid");

        // obtemos o ViewModel pois é nele que está o método que se conecta ao servior web.
        ViewEventViewModel viewEventViewModel = new ViewModelProvider(this).get(ViewEventViewModel.class);

        // O ViewModel possui o método getProductDetailsLD, que obtém os detalhes de um produto em
        // específico do servidor web.
        //
        // O método getProductDetailsLD retorna um LiveData, que na prática é um container que avisa
        // quando o resultado do servidor chegou. Ele guarda os detalhes de um produto que o servidor
        // entregou para a app.
        LiveData<Event> event = viewEventViewModel.getEventDetailsLD(pid);

        // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado contendo uma produto
        // será guardado dentro do LiveData. Neste momento o
        // LiveData avisa que o produto chegou chamando o método onChanged abaixo.
        event.observe(this, new Observer<Event>() {
            @Override
            public void onChanged(Event event) {

                // product contém os detalhes do produto que foram entregues pelo servidor web
                if(event != null) {

                    // aqui nós obtemos a imagem do produto. A imagem não vem logo de cara. Primeiro
                    // obtemos os detalhes do produto. Uma vez recebidos os campos de id, nome, preço,
                    // descrição, criado por, usamos o id para obter a imagem do produto em separado.
                    // A classe ImageCache obtém a imagem de um produto específico, guarda em um cache
                    // o seta em um ImageView fornecido.
                    ImageView imvProductPhoto = findViewById(R.id.imFotoEventoF);
                    int imgHeight = (int) ViewEventsDoMomentoActivity.this.getResources().getDimension(R.dimen.img_carrossel_height);
                    ImageCache.loadImageUrlToImageView(ViewEventsDoMomentoActivity.this, event.imagem, imvProductPhoto, -1, imgHeight);

                    // Abaixo nós obtemos os dados do produto e setamos na interfa de usuário
                    TextView tvNomeF = findViewById(R.id.tvNomeF);
                    tvNomeF.setText(event.nome);

                    TextView tvObjetivoF = findViewById(R.id.tvObjetivoF);
                    tvObjetivoF.setText(event.objetivo);

                    TextView tvDataF = findViewById(R.id.tvDataF);
                    tvObjetivoF.setText(event.data);

                }
                else {
                    Toast.makeText(ViewEventsDoMomentoActivity.this, "Não foi possível obter os detalhes do evento", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}


