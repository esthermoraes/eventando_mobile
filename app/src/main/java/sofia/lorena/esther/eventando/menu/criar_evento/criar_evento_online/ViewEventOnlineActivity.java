package sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_online;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.model.EventOnline;
import sofia.lorena.esther.eventando.model.ViewEventOnlineViewModel;
import sofia.lorena.esther.eventando.util.ImageCache;

public class ViewEventOnlineActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_eventos_online_nao_editavel);

        // Para obter os detalhes do produto, a app envia o id do produto ao servidor web. Este
        // último responde com os detalhes do produto referente ao pid.

        // O pid do produto é enviado para esta tela quando o produto é clicado na tela de Home.
        // Aqui nós obtemos o pid.
        Intent i = getIntent();
        String pid = i.getStringExtra("pid");



        // obtemos o ViewModel pois é nele que está o método que se conecta ao servior web.
        ViewEventOnlineViewModel viewEventOnlineViewModel = new ViewModelProvider(this).get(ViewEventOnlineViewModel.class);

        ImageButton icEstrelaF2 = findViewById(R. id.icEstrelaF2);
        icEstrelaF2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveData<Boolean> favoritarEventLD = viewEventOnlineViewModel.favoritar(pid);
                favoritarEventLD.observe(this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if(aBoolean) {
                            //setar estrelinha diferente
                        }
                    }
                });
            }
        });

        // O ViewModel possui o método getProductDetailsLD, que obtém os detalhes de um produto em
        // específico do servidor web.
        //
        // O método getProductDetailsLD retorna um LiveData, que na prática é um container que avisa
        // quando o resultado do servidor chegou. Ele guarda os detalhes de um produto que o servidor
        // entregou para a app.
        LiveData<EventOnline> eventOnline = viewEventOnlineViewModel.getEventOnlineDetailsLD(pid);

        // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado contendo uma produto
        // será guardado dentro do LiveData. Neste momento o
        // LiveData avisa que o produto chegou chamando o método onChanged abaixo.
        eventOnline.observe(this, new Observer<EventOnline>() {
            @Override
            public void onChanged(EventOnline eventOnline) {

                // product contém os detalhes do produto que foram entregues pelo servidor web
                if(eventOnline != null) {

                    // aqui nós obtemos a imagem do produto. A imagem não vem logo de cara. Primeiro
                    // obtemos os detalhes do produto. Uma vez recebidos os campos de id, nome, preço,
                    // descrição, criado por, usamos o id para obter a imagem do produto em separado.
                    // A classe ImageCache obtém a imagem de um produto específico, guarda em um cache
                    // o seta em um ImageView fornecido.
                    ImageView imvProductPhoto = findViewById(R.id.imFotoEvento);
                    int imgHeight = (int) ViewEventOnlineActivity.this.getResources().getDimension(R.dimen.img_height);
                    ImageCache.loadImageUrlToImageView(ViewEventOnlineActivity.this, eventOnline.imagem, imvProductPhoto, -1, imgHeight);

                    // Abaixo nós obtemos os dados do produto e setamos na interfa de usuário
                    TextView tvNomeF = findViewById(R.id.tvNomeEvento);
                    tvNomeF.setText(eventOnline.nome);

                    TextView tvPrivacidade = findViewById(R.id.tbPrivacidade);
                    tvPrivacidade.setText(eventOnline.privacidade);

                    TextView tvObjetivoF = findViewById(R.id.tvObjetivoO);
                    tvObjetivoF.setText(eventOnline.objetivo);

                    TextView tvDataPrevista = findViewById(R.id.tvDataPrevistaO);
                    tvDataPrevista.setText(eventOnline.data);

                    TextView tvHora = findViewById(R.id.tvHorarioO);
                    tvHora.setText(eventOnline.hora);

                    TextView tvPlataforma = findViewById(R.id.tvPlataformaO);
                    tvPlataforma.setText(eventOnline.plataforma);

                    TextView tvLink = findViewById(R.id.tvLinkO);
                    tvLink.setText(eventOnline.link);

                    TextView tvAtracoes = findViewById(R.id.tvAtracoes0);
                    tvAtracoes.setText(eventOnline.atracoes);

                    TextView tvTipoContato = findViewById(R.id.tvTipoContato0);
                    tvTipoContato.setText(eventOnline.tipo_contato);

                    TextView tvContato0 = findViewById(R.id.tvContato0);
                    tvContato0.setText(eventOnline.contato);


                }
                else {
                    Toast.makeText(ViewEventOnlineActivity.this, "Não foi possível obter os detalhes do evento", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
