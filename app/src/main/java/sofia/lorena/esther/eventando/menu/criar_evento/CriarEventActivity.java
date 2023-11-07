package sofia.lorena.esther.eventando.menu.criar_evento;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_online.CriarEventOnlineFragment;
import sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial.CriarEventPresencialFragment;
import sofia.lorena.esther.eventando.menu.perfil.ProfileNaoEditavelFragment;
import sofia.lorena.esther.eventando.model.CriarEventViewModel;
import sofia.lorena.esther.eventando.model.HomeViewModel;
import sofia.lorena.esther.eventando.model.ViewEventViewModel;

public class CriarEventActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);
        setTitle("CRIAR EVENTO");

        RadioGroup radioGroup = findViewById(R.id.rdFormato);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.btnPresencial){
                    CriarEventPresencialFragment criarEventPresencialFragment = new CriarEventPresencialFragment();
                    setFragment(criarEventPresencialFragment, R.id.flInfoBasicas);
                }
                else{
                    CriarEventOnlineFragment criarEventOnlineFragment = new CriarEventOnlineFragment();
                    setFragment(criarEventOnlineFragment, R.id.flInfoBasicas);
                }
            }
        });


        final CriarEventViewModel vm = new ViewModelProvider(this).get(CriarEventViewModel.class);
        int optionsSelected = vm.getSelectedEventType();
        if(optionsSelected == 0){
            RadioButton btnPresencial = findViewById(R.id.btnPresencial);
            btnPresencial.setChecked(true);
        }
        else{
            RadioButton btnOnline = findViewById(R.id.btnOnline);
            btnOnline.setChecked(true);
        }

    }

    public void setFragment(Fragment fragment, int frameLayoutId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(frameLayoutId, fragment)
                .addToBackStack(null)
                .commit();
    }
}