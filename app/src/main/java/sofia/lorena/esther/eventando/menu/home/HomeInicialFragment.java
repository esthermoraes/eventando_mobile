package sofia.lorena.esther.eventando.menu.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagingData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.adapter.EventComparator;
import sofia.lorena.esther.eventando.adapter.EventDoMomentoComparator;
import sofia.lorena.esther.eventando.adapter.EventosAdapter;
import sofia.lorena.esther.eventando.model.Event;
import sofia.lorena.esther.eventando.model.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeInicialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeInicialFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeInicialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_home_inicial.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeInicialFragment newInstance() {
        HomeInicialFragment fragment = new HomeInicialFragment();
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
        return inflater.inflate(R.layout.fragment_home_inicial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configura o RecyclerView no fragmento
        RecyclerView rvListEvents = view.findViewById(R.id.rvListEvents);
        rvListEvents.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvListEvents.setLayoutManager(layoutManager);
        EventosAdapter eventosAdapter = new EventosAdapter((HomeActivity) getActivity(), new EventComparator());
        rvListEvents.setAdapter(eventosAdapter);

        RecyclerView rvEventsDoMomento = view.findViewById(R.id.rvEventsDoMomento);
        rvEventsDoMomento.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvEventsDoMomento.setLayoutManager(layoutManager);
        EventosDoMomentoAdapter eventosDoMomentoAdapter = new EventosDoMomentoAdapter((HomeActivity) getActivity(), new EventDoMomentoComparator();
        rvEventsDoMomento.setAdapter(eventosDoMomentoAdapter);

        // Obtemos o ViewModel
        HomeViewModel homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        // Obtém o LiveData dos eventos
        LiveData<PagingData<Event>> eventsLd = homeViewModel.getEventsLiveData();

        // Observa o LiveData dos eventos
        eventsLd.observe(getViewLifecycleOwner(), new Observer<PagingData<Event>>() {
            @Override
            public void onChanged(PagingData<Event> eventPagingData) {
                eventosAdapter.submitData(getLifecycle(), eventPagingData);
            }
        });
    }


}