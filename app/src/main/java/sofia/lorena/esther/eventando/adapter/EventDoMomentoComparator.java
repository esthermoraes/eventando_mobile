package sofia.lorena.esther.eventando.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import sofia.lorena.esther.eventando.model.Event;

    public class EventDoMomentoComparator extends DiffUtil.ItemCallback<Event> {
        @Override
        public boolean areItemsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Event oldItem, @NonNull Event newItem) {
            return oldItem.id.equals(newItem.id) &&
                    oldItem.nome.equals(newItem.nome) &&
                    oldItem.data.equals(newItem.data);
        }
}
