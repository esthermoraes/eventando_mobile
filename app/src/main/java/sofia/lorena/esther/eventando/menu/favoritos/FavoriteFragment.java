package sofia.lorena.esther.eventando.menu.favoritos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.adapter.FavoritosAdapter;
import sofia.lorena.esther.eventando.menu.home.HomeActivity;
import sofia.lorena.esther.eventando.model.Event;
import sofia.lorena.esther.eventando.model.HomeViewModel;
import sofia.lorena.esther.eventando.util.Util;

public class FavoriteFragment extends Fragment {
    HomeActivity homeActivity;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configura o RecyclerView no fragmento
        RecyclerView rvFavorites = view.findViewById(R.id.rvFavorites);
        rvFavorites.setHasFixedSize(true);
        float w = getResources().getDimension(R.dimen.img_carrossel_width);
        int numberOfColumns = 3;
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), numberOfColumns);
        rvFavorites.setLayoutManager(layoutManager);
        FavoritosAdapter favoritosAdapter = new FavoritosAdapter((HomeActivity) getActivity());
        rvFavorites.setAdapter(favoritosAdapter);

        // Obtemos o ViewModel
        HomeViewModel homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        // Obtém o LiveData dos eventos
        LiveData<PagingData<Event>> favoritosLd = homeViewModel.getFavoritosLiveData();

        // Observa o LiveData dos eventos
        favoritosLd.observe(getViewLifecycleOwner(), new Observer<PagingData<Event>>() {
            @Override
            public void onChanged(PagingData<Event> eventPagingData) {
                favoritosAdapter.submitData(getViewLifecycleOwner().getLifecycle(), eventPagingData);
            }
        });
    }
}
