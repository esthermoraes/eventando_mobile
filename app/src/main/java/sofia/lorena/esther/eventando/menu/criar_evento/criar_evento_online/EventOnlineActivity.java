package sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_online;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sofia.lorena.esther.eventando.R;

public class EventOnlineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home_inicial);

        Intent i = getIntent();
        String id = i.getStringExtra("id");



    }
}