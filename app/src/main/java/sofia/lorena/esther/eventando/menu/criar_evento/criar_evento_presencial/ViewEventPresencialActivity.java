package sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial;

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
import sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_online.ViewEventOnlineActivity;
import sofia.lorena.esther.eventando.model.EventPresencial;
import sofia.lorena.esther.eventando.model.ViewEventOnlineViewModel;
import sofia.lorena.esther.eventando.model.ViewEventPresencialViewModel;
import sofia.lorena.esther.eventando.util.ImageCache;

public class ViewEventPresencialActivity extends AppCompatActivity{
    private boolean isFavorito = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_eventos_presencial_nao_editavel);

        Intent i = getIntent();
        String pid = i.getStringExtra("pid");

        ViewEventPresencialViewModel viewEventPresencialViewModel = new ViewModelProvider(this).get(ViewEventPresencialViewModel.class);

        ImageButton icEstrelaF = findViewById(R.id.icEstrelaF);

        // Adiciona um clique ao botão para alternar entre favoritar e desfavoritar
        icEstrelaF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorito) {
                    // Se já foi favoritado, desfavorize
                    LiveData<Boolean> desfavoritarEventLD = viewEventPresencialViewModel.desfavoritar(pid);
                    desfavoritarEventLD.observe(ViewEventPresencialActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean desfavoritado) {
                            if (desfavoritado) {
                                // Se foi desfavoritado, exiba o ícone de estrela vazia
                                icEstrelaF.setImageResource(R.drawable.baseline_star_border_24);
                                isFavorito = false;
                            }
                        }
                    });
                } else {
                    // Se não foi favoritado, favorize
                    LiveData<Boolean> favoritarEventLD = viewEventPresencialViewModel.favoritar(pid);
                    favoritarEventLD.observe(ViewEventPresencialActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean favoritado) {
                            if (favoritado) {
                                // Se foi favoritado, exiba o ícone de estrela cheia
                                icEstrelaF.setImageResource(R.drawable.baseline_star_24);
                                isFavorito = true;
                            }
                        }
                    });
                }
            }
        });


        // O ViewModel possui o método getProductDetailsLD, que obtém os detalhes de um produto em
        // específico do servidor web.
        //
        // O método getProductDetailsLD retorna um LiveData, que na prática é um container que avisa
        // quando o resultado do servidor chegou. Ele guarda os detalhes de um produto que o servidor
        // entregou para a app.
        LiveData<EventPresencial> eventPresencial = viewEventPresencialViewModel.getEventPresencialDetailsLD(pid);

        // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado contendo uma produto
        // será guardado dentro do LiveData. Neste momento o
        // LiveData avisa que o produto chegou chamando o método onChanged abaixo.
        eventPresencial.observe(this, new Observer<EventPresencial>() {
            @Override
            public void onChanged(EventPresencial eventPresencial) {

                // product contém os detalhes do produto que foram entregues pelo servidor web
                if(eventPresencial != null) {

                    // aqui nós obtemos a imagem do produto. A imagem não vem logo de cara. Primeiro
                    // obtemos os detalhes do produto. Uma vez recebidos os campos de id, nome, preço,
                    // descrição, criado por, usamos o id para obter a imagem do produto em separado.
                    // A classe ImageCache obtém a imagem de um produto específico, guarda em um cache
                    // o seta em um ImageView fornecido.
                    ImageView imvProductPhoto = findViewById(R.id.imFotoEvento);
                    int imgHeight = (int) sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial.ViewEventPresencialActivity.this.getResources().getDimension(R.dimen.img_height);
                    ImageCache.loadImageUrlToImageView(sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial.ViewEventPresencialActivity.this, eventPresencial.imagem, imvProductPhoto, -1, imgHeight);

                    // Abaixo nós obtemos os dados do produto e setamos na interfa de usuário
                    TextView tvNomeF = findViewById(R.id.tvNomeEvento);
                    tvNomeF.setText(eventPresencial.nome);

                    TextView tvPrivacidade = findViewById(R.id.tbPrivacidade);
                    tvPrivacidade.setText(eventPresencial.privacidade);

                    TextView tvObjetivoF = findViewById(R.id.tvObjetivoP);
                    tvObjetivoF.setText(eventPresencial.objetivo);

                    TextView tvDataPrevista = findViewById(R.id.tvDataPrevistaP);
                    tvDataPrevista.setText(eventPresencial.data);

                    TextView tvHora = findViewById(R.id.tvHorarioP);
                    tvHora.setText(eventPresencial.hora);

                    TextView tvCepP = findViewById(R.id.tvCepP);
                    tvCepP.setText(eventPresencial.cep);

                    TextView tvEstadoP = findViewById(R.id.tvEstadoP);
                    tvEstadoP.setText(eventPresencial.estado);

                    TextView tvCidadeP = findViewById(R.id.tvCidadeP);
                    tvCidadeP.setText(eventPresencial.cidade);

                    TextView tvBairroP = findViewById(R.id.tvBairroP);
                    tvBairroP.setText(eventPresencial.bairro);

                    TextView tvTipoLogradouro = findViewById(R.id.tvTipoLogradouro);
                    tvTipoLogradouro.setText(eventPresencial.tipoLogradouro);

                    TextView tvLogradouroP = findViewById(R.id.tvLogradouroP);
                    tvLogradouroP.setText(eventPresencial.logradouro);

                    TextView tvNumeroP = findViewById(R.id.tvNumeroP);
                    tvNumeroP.setText(eventPresencial.numero);

                    TextView tvBuffetP = findViewById(R.id.tvBuffetP);
                    tvBuffetP.setText(eventPresencial.buffet);

                    TextView tvAtracoesP = findViewById(R.id.tvAtracoesP);
                    tvAtracoesP.setText(eventPresencial.atracoes);

                    TextView tvTipoContatoP = findViewById(R.id.tvTipoContatoP);
                    tvTipoContatoP.setText(eventPresencial.tipo_contato);

                    TextView tvContatoP = findViewById(R.id.tvContatoP);
                    tvContatoP.setText(eventPresencial.contato);



                }
                else {
                    Toast.makeText(sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial.ViewEventPresencialActivity.this, "Não foi possível obter os detalhes do evento", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}


