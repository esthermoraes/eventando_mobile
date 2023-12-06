//package sofia.lorena.esther.eventando.adapter;
//
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import sofia.lorena.esther.eventando.R;
//import sofia.lorena.esther.eventando.menu.home.HomeActivity;
//import sofia.lorena.esther.eventando.model.Event;
//import sofia.lorena.esther.eventando.util.ImageCache;
//
//public class PesquisaAdapter extends RecyclerView.Adapter {
//    public HomeActivity homeActivity;
//    public List<Event> events;
//
//    public PesquisaAdapter(HomeActivity homeActivity, List<Event> produtos) {
//        this.homeActivity = homeActivity;
//        this.events = events;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(homeActivity);
//        View v = inflater.inflate(R.layout.event_lista_item, parent, false);
//        return new PesquisaViewHolder(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//
//        Event event = this.get(position);
//        View v = holder.itemView;
//
//
//        TextView tvNomeF = holder.itemView.findViewById(R.id.tvNomeF);
//        tvNomeF.setText(event.nome);
//
//        TextView tvDataF = holder.itemView.findViewById(R.id.tvDataF);
//        tvDataF.setText(event.data);
//
//        TextView tvObjetivoF = holder.itemView.findViewById(R.id.tvObjetivoF);
//        tvObjetivoF.setText(event.objetivo);
//
//        // preenche o campo de foto
//        int w = (int) homeActivity.getResources().getDimension(R.dimen.thumb_width);
//        int h = (int) homeActivity.getResources().getDimension(R.dimen.thumb_height);
//
//        ImageView imFotoEventoF = holder.itemView.findViewById(R.id.imFotoEventoF);
//
//        // somente agora a imagem é obtida do servidor. Caso a imagem já esteja salva no cache da app,
//        // não baixamos ela de novo
//        ImageCache.loadImageUrlToImageView(homeActivity, event.imagem, imFotoEventoF, w, h);
//
//
//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(homeActivity, Event.class);
//                i.putExtra("codigo_evento", event.id);
//                homeActivity.startActivity(i);
//                homeActivity.finish();
//            }
//        });
//
//
//    }
//}
//
