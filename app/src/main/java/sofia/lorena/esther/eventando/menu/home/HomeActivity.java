package sofia.lorena.esther.eventando.menu.home;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.menu.favoritos.FavoriteFragment;
import sofia.lorena.esther.eventando.menu.meus_eventos.MyEventsFragment;
import sofia.lorena.esther.eventando.menu.perfil.ProfileNaoEditavelFragment;
import sofia.lorena.esther.eventando.model.HomeViewModel;


public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

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
                        break;
                    case R.id.itemMeusEventos:
                        MyEventsFragment myEventsFragment = new MyEventsFragment();
                        setFragment(myEventsFragment, R.id.flMenu);
                        break;
                    case R.id.itemHome:
                        HomeFragment homeFragment = new HomeFragment();
                        setFragment(homeFragment, R.id.flMenu);
                        break;
                    case R.id.itemFavoritos:
                        FavoriteFragment favoriteFragment = new FavoriteFragment();
                        setFragment(favoriteFragment, R.id.flMenu);
                        break;
                }
                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(menuItem);
    }

    // Método para definir o fragmento na tela
    public void setFragment(Fragment fragment, int frameLaoutId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(frameLaoutId, fragment)
                .addToBackStack(null)
                .commit();
    }
}
