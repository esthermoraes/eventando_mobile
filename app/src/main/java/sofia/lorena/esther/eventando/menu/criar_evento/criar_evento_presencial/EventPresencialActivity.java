package sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sofia.lorena.esther.eventando.R;

public class EventPresencialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_presencial);

        Intent i = getIntent();
        String id = i.getStringExtra("id");


    }
}