package sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_online;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.model.EventOnline;
import sofia.lorena.esther.eventando.util.ImageCache;

public class VisualizarMyEventsOnlineNaoEditavelActivity extends AppCompatActivity {
    EventOnline eventOnline;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_meus_eventos_online_nao_editavel);


    }
}
