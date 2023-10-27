package sofia.lorena.esther.eventando;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import sofia.lorena.esther.eventando.acessar.LoginActivity;
import sofia.lorena.esther.eventando.menu.home.HomeActivity;
import sofia.lorena.esther.eventando.util.Config;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Resto do código da atividade principal
        // Se o usuário ainda não logou, então não existe informação de login guardada na app.
        // Então a app é redirecionada para a tela de login.
        if (Config.getLogin(MainActivity.this).isEmpty()) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        // Se o usuário já logou, então a informação de login está guardada na app. Então
        // a app é redirecionada para a tela principal da app (HomeActivity)
        else {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
            finish();
        }
    }
}




