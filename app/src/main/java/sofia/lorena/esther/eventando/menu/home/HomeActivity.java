package sofia.lorena.esther.eventando.menu.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.adapter.EventosAdapter;
import sofia.lorena.esther.eventando.menu.criar_evento.CriarEventActivity;
import sofia.lorena.esther.eventando.menu.favoritos.FavoriteFragment;
import sofia.lorena.esther.eventando.menu.meus_eventos.MyEventsFragment;
import sofia.lorena.esther.eventando.menu.perfil.ProfileNaoEditavelFragment;
import sofia.lorena.esther.eventando.model.HomeViewModel;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    EventosAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bnvMenu);
        final HomeViewModel vm = new ViewModelProvider(this).get(HomeViewModel.class);
        Integer menuItem = vm.getSelectedNavigationOpId();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                vm.setNavigationOpSelected(item.getItemId());
                switch (item.getItemId()) {
                    case R.id.itemPerfil:
                        ProfileNaoEditavelFragment profileNaoEditavelFragment = new ProfileNaoEditavelFragment();
                        setFragment(profileNaoEditavelFragment, R.id.flMenu);
                        setTitle("PERFIL");
                        break;
                    case R.id.itemMeusEventos:
                        MyEventsFragment myEventsFragment = new MyEventsFragment();
                        setFragment(myEventsFragment, R.id.flMenu);
                        setTitle("MEUS EVENTOS");
                        break;
                    case R.id.itemHome:
                        HomeFragment homeFragment = new HomeFragment();
                        setFragment(homeFragment, R.id.flMenu);
                        setTitle("HOME");
                        break;
                    case R.id.itemFavoritos:
                        FavoriteFragment favoriteFragment = new FavoriteFragment();
                        setFragment(favoriteFragment, R.id.flMenu);
                        setTitle("FAVORITOS");
                        break;
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(menuItem);

        FloatingActionButton fabCriarEvento = findViewById(R.id.fabCriarEvento);
        fabCriarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, CriarEventActivity.class);
                startActivity(i);
            }
        });

        // Adicione o fragmento ao contêiner
        HomeInicialFragment homeInicialfragment = HomeInicialFragment.newInstance();
        setFragment(homeInicialfragment, R.id.flHome);
    }

    // Método para definir o fragmento na tela
    public void setFragment(Fragment fragment, int frameLayoutId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(frameLayoutId, fragment)
                .addToBackStack(null)
                .commit();
    }
}
