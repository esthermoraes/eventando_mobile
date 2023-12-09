package sofia.lorena.esther.eventando.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.menu.home.HomeActivity;
import sofia.lorena.esther.eventando.model.Event;
import sofia.lorena.esther.eventando.util.ImageCache;

public class FavoritosAdapter extends PagingDataAdapter<Event, RecyclerView.ViewHolder> {

    HomeActivity homeActivity;

    public FavoritosAdapter(HomeActivity homeActivity) {
        super(new DiffUtil.ItemCallback<Event>() {
            @Override
            public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                return oldItem.getId().equals(newItem.getId());
            }

            @Override
            public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.homeActivity = homeActivity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.event_carousel_item, parent, false);
        return new RecyclerView.ViewHolder(v) {};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Event event = getItem(position);

        TextView tvNomeF = holder.itemView.findViewById(R.id.tvNomeF);
        tvNomeF.setText(event.nome());

        TextView tvDataF = holder.itemView.findViewById(R.id.tvDataF);
        tvDataF.setText(event.data());

        // preenche o campo de foto
        int w = (int) homeActivity.getResources().getDimension(R.dimen.thumb_width);
        int h = (int) homeActivity.getResources().getDimension(R.dimen.thumb_height);

        ImageView imFotoEventoF = holder.itemView.findViewById(R.id.imFotoEventoF);

        // somente agora a imagem é obtida do servidor. Caso a imagem já esteja salva no cache da app,
        // não baixamos ela de novo
        ImageCache.loadImageUrlToImageView(homeActivity, event.img(), imFotoEventoF, w, h);

        // ao clicar em um item da lista, navegamos para a tela que mostra os detalhes do produto
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (event.formato().equals("online")) {
                    homeActivity.startViewEventOnlineAcitivity(event.getId());
                } else {
                    homeActivity.startViewEventPresencialAcitivity(event.getId());
                }
            }
        });
    }
}
