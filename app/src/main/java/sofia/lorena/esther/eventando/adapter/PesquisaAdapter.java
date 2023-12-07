package sofia.lorena.esther.eventando.adapter;

import android.content.Intent;
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

public class PesquisaAdapter extends PagingDataAdapter<Event, MyViewHolder> {

    HomeActivity homeActivity;

    public PesquisaAdapter(HomeActivity homeActivity) {
        super(new DiffUtil.ItemCallback<Event>() {
            @Override
            public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                // Verifique se os identificadores dos itens são os mesmos
                return oldItem.id.equals(newItem.id);
            }

            @Override
            public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
                // Verifique se o conteúdo dos itens é o mesmo
                return oldItem.equals(newItem);
            }
        });
        this.homeActivity = homeActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.event_lista_item, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = getItem(position);
        if (event != null) {
            View v = holder.itemView;

            TextView tvNomeF = holder.itemView.findViewById(R.id.tvNomeF);
            tvNomeF.setText(event.nome);

            TextView tvDataF = holder.itemView.findViewById(R.id.tvDataF);
            tvDataF.setText(event.data);

            TextView tvObjetivoF = holder.itemView.findViewById(R.id.tvObjetivoF);
            tvObjetivoF.setText(event.objetivo);

            int w = (int) homeActivity.getResources().getDimension(R.dimen.thumb_width);
            int h = (int) homeActivity.getResources().getDimension(R.dimen.thumb_height);

            ImageView imFotoEventoF = holder.itemView.findViewById(R.id.imFotoEventoF);

            ImageCache.loadImageUrlToImageView(homeActivity, event.imagem, imFotoEventoF, w, h);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(homeActivity, Event.class);
                    i.putExtra("codigo_evento", event.id);
                    homeActivity.startActivity(i);
                    homeActivity.finish();
                }
            });
        }
    }
}
