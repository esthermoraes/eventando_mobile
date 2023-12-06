package sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial;

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
import sofia.lorena.esther.eventando.model.EventPresencial;
import sofia.lorena.esther.eventando.model.ViewEventPresencialViewModel;
import sofia.lorena.esther.eventando.util.ImageCache;

public class ViewEventPresencialActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_eventos_presencial_nao_editavel);

        // Para obter os detalhes do produto, a app envia o id do produto ao servidor web. Este
        // último responde com os detalhes do produto referente ao pid.

        // O pid do produto é enviado para esta tela quando o produto é clicado na tela de Home.
        // Aqui nós obtemos o pid.
        Intent i = getIntent();
        String pid = i.getStringExtra("pid");

        // obtemos o ViewModel pois é nele que está o método que se conecta ao servior web.
        ViewEventPresencialViewModel viewEventPresencialViewModel = new ViewModelProvider(this).get(ViewEventPresencialViewModel.class);

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


