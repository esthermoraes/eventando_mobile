package sofia.lorena.esther.eventando.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.menu.home.HomeActivity;
import sofia.lorena.esther.eventando.model.Event;
import sofia.lorena.esther.eventando.util.ImageCache;

public class PesquisaAdapter extends RecyclerView.Adapter {

    HomeActivity homeActivity;

    List<Event> events;

    public PesquisaAdapter(HomeActivity homeActivity, List<Event> events) {
        this.homeActivity = homeActivity;
        this.events =  events;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Event event = events.get(position);
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

    @Override
    public int getItemCount() {
        return events.size();
    }

}
