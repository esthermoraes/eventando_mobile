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
    private boolean isFavorito = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_eventos_online_nao_editavel);

        Intent i = getIntent();
        String pid = i.getStringExtra("pid");

        ViewEventOnlineViewModel viewEventOnlineViewModel = new ViewModelProvider(this).get(ViewEventOnlineViewModel.class);

        ImageButton icEstrelaF2 = findViewById(R.id.icEstrelaF2);

        // Adiciona um clique ao botão para alternar entre favoritar e desfavoritar
        icEstrelaF2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFavorito) {
                    // Se já foi favoritado, desfavorize
                    LiveData<Boolean> desfavoritarEventLD = viewEventOnlineViewModel.desfavoritar(pid);
                    desfavoritarEventLD.observe(ViewEventOnlineActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean desfavoritado) {
                            if (desfavoritado) {
                                // Se foi desfavoritado, exiba o ícone de estrela vazia
                                icEstrelaF2.setImageResource(R.drawable.baseline_star_border_24);
                                isFavorito = false;
                            }
                        }
                    });
                } else {
                    // Se não foi favoritado, favorize
                    LiveData<Boolean> favoritarEventLD = viewEventOnlineViewModel.favoritar(pid);
                    favoritarEventLD.observe(ViewEventOnlineActivity.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean favoritado) {
                            if (favoritado) {
                                // Se foi favoritado, exiba o ícone de estrela cheia
                                icEstrelaF2.setImageResource(R.drawable.baseline_star_24);
                                isFavorito = true;
                            }
                        }
                    });
                }
            }
        });

        LiveData<EventOnline> eventOnline = viewEventOnlineViewModel.getEventOnlineDetailsLD(pid);

        eventOnline.observe(this, new Observer<EventOnline>() {
            @Override
            public void onChanged(EventOnline eventOnline) {
                if (eventOnline != null) {
                    ImageView imvProductPhoto = findViewById(R.id.imFotoEvento);
                    int imgHeight = (int) getResources().getDimension(R.dimen.img_height);
                    ImageCache.loadImageUrlToImageView(ViewEventOnlineActivity.this, eventOnline.imagem, imvProductPhoto, -1, imgHeight);

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
                } else {
                    Toast.makeText(ViewEventOnlineActivity.this, "Não foi possível obter os detalhes do evento", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
