package sofia.lorena.esther.eventando.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.menu.home.HomeActivity;
import sofia.lorena.esther.eventando.model.Event;
import sofia.lorena.esther.eventando.util.ImageCache;

public class EventosAdapter extends PagingDataAdapter<Event, MyViewHolder> {

    HomeActivity homeActivity;

    public EventosAdapter(HomeActivity homeActivity, @NonNull DiffUtil.ItemCallback<Event> diffCallback) {
        super(diffCallback);
        this.homeActivity = homeActivity;
    }

    /**
     * Cria os elementos de UI referente a um item da lista
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.event_lista_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    /**
     * Preenche um item da lista
     */
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = this.getItem(position);

        TextView tvNomeF = holder.itemView.findViewById(R.id.tvNomeF);
        tvNomeF.setText(event.nome);

        TextView tvDataF = holder.itemView.findViewById(R.id.tvDataF);
        tvDataF.setText(event.data);

        TextView tvObjetivoF = holder.itemView.findViewById(R.id.tvObjetivoF);
        tvObjetivoF.setText(event.objetivo);

        // preenche o campo de foto
        int w = (int) homeActivity.getResources().getDimension(R.dimen.thumb_width);
        int h = (int) homeActivity.getResources().getDimension(R.dimen.thumb_height);

        ImageView imFotoEventoF = holder.itemView.findViewById(R.id.imFotoEventoF);

        // somente agora a imagem é obtida do servidor. Caso a imagem já esteja salva no cache da app,
        // não baixamos ela de novo
        ImageCache.loadImageUrlToImageView(homeActivity, event.imagem, imFotoEventoF, w, h);

        // ao clicar em um item da lista, navegamos para a tela que mostra os detalhes do produto
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(event.formato.equals("online")) {
                    homeActivity.startViewEventOnlineAcitivity(event.id);
                }
                else {
                    homeActivity.startViewEventPresencialAcitivity(event.id);
                }

            }
        });
    }
}
