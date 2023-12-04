package sofia.lorena.esther.eventando.menu.meus_eventos;

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
import sofia.lorena.esther.eventando.adapter.MyEventsAdapter;
import sofia.lorena.esther.eventando.adapter.MyEventsComparator;
import sofia.lorena.esther.eventando.menu.home.HomeActivity;
import sofia.lorena.esther.eventando.model.Event;
import sofia.lorena.esther.eventando.model.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyEventsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyEventsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyEventsFragment newInstance() {
        MyEventsFragment fragment = new MyEventsFragment();
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
        return inflater.inflate(R.layout.fragment_my_events, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Configura o RecyclerView no fragmento
        RecyclerView rvEventsR = view.findViewById(R.id.rvMyEvents);
        rvEventsR.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvEventsR.setLayoutManager(layoutManager);
        MyEventsAdapter myEventsAdapter = new MyEventsAdapter((HomeActivity) getActivity(), new MyEventsComparator());
        rvEventsR.setAdapter(myEventsAdapter);


        // Obtemos o ViewModel
        HomeViewModel homeViewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);

        // Obtém o LiveData dos eventos
        LiveData<PagingData<Event>> myEventsLd = homeViewModel.getEventsLiveData();

        // Observa o LiveData dos eventos
        myEventsLd.observe(getViewLifecycleOwner(), new Observer<PagingData<Event>>() {
            @Override
            public void onChanged(PagingData<Event> eventPagingData) {
                myEventsAdapter.submitData(getLifecycle(), eventPagingData);
            }
        });
    }
}