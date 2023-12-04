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

import java.util.List;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.adapter.EventComparator;
import sofia.lorena.esther.eventando.adapter.EventDoMomentoAdapter;
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

        RecyclerView rvEventsDoMomento = view.findViewById(R.id.rvEventsDoMomento);
        rvEventsDoMomento.setHasFixedSize(true);
        RecyclerView.LayoutManager    layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rvEventsDoMomento.setLayoutManager(layoutManager1);



        LiveData<List<Event>> eventsDoMomentoLd = homeViewModel.getEventsDoMomento();
        // Observa o LiveData dos eventos
        eventsDoMomentoLd.observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events) {
                EventDoMomentoAdapter eventDoMomentoAdapter = new EventDoMomentoAdapter((HomeActivity) getActivity(), events);
                rvEventsDoMomento.setAdapter(eventDoMomentoAdapter);
            }
        });
    }

}