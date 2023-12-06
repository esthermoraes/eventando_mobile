//package sofia.lorena.esther.eventando.menu.home;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import sofia.lorena.esther.eventando.R;
//import sofia.lorena.esther.eventando.adapter.PesquisaAdapter;
//import sofia.lorena.esther.eventando.model.Event;
//import sofia.lorena.esther.eventando.model.HomeViewModel;
//
//
//public class BuscarEventoFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_CATEGORIA = "categoria";
//    private static final String ARG_PESQUISA = "pesquisa";
//
//    // TODO: Rename and change types of parameters
//    private String pesquisa;
//
//    public BuscarEventoFragment() {
//        // Required empty public constructor
//    }
//
//
//    public static BuscarEventoFragment newInstance(String pesquisa) {
//        BuscarEventoFragment fragment = new BuscarEventoFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PESQUISA, pesquisa);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            pesquisa = getArguments().getString(ARG_PESQUISA);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_buscar_evento, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//        RecyclerView rvPesquisa = (RecyclerView) view.findViewById(R.id.rvEventsBuscados);
//        rvPesquisa.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        //chamar metodo que ira preencher a lista de produtos
//        //quando o PesquisaFragment for criado, essa funcao vai ser chamada e setar o adapter da rvPesquisa
//        HomeActivity homeActivity = (HomeActivity) getActivity();
//
//        HomeViewModel mViewModel = new ViewModelProvider(homeActivity).get(HomeViewModel.class);
//
//        if (pesquisa != "") {
//
//            LiveData<List<Event>> prodLiveData = mViewModel.getEventPesquisaLD(this.pesquisa);
//            prodLiveData.observe(getViewLifecycleOwner(), new Observer<List<Event>>() {
//                @Override
//                public void onChanged(List<Event> eventos) {
//                    PesquisaAdapter pesquisaAdapter = new PesquisaAdapter(homeActivity, eventos);
//                    rvPesquisa.setAdapter(pesquisaAdapter);
//            }
//        });
//    }
//}
