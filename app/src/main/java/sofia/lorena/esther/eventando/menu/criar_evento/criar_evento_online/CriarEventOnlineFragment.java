package sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_online;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.menu.criar_evento.CriarEventActivity;
import sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial.CriarEventPresencialFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CriarEventOnlineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CriarEventOnlineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CriarEventActivity criarEventActivity;
    public CriarEventOnlineFragment(CriarEventActivity criarEventActivity) {
        // Required empty public constructor
        this.criarEventActivity = criarEventActivity;
    }

        // Required empty public constructor


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsCriarOnlineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CriarEventOnlineFragment newInstance(CriarEventActivity criarEventActivity) {
        CriarEventOnlineFragment fragment = new CriarEventOnlineFragment(CriarEventActivity criarEventActivity);
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
        return inflater.inflate(R.layout.fragment_events_criar_online, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button btnCriar = view.findViewById(R.id.btnCriarO);
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnCriar.setEnabled(false);
                Spinner spPlataformaO = (Spinner)view.findViewById(R.id.spPlataformaO);
                int position = spPlataformaO.getSelectedItemPosition();
                if(position == 0) {
                    Toast.makeText(CriarEventActivity.this, "Campo de plataforma não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }

                EditText etLinkCO =  view.findViewById(R.id.etLinkCO);
                final String newetLinkCO = etLinkCO.getText().toString();
                if(newetLinkCO.isEmpty()) {
                    Toast.makeText(CriarEventActivity.this, "Campo de link não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }
                criarEventActivity.cadastrarEventoOnline(position, newetLinkCO, btnCriar);
            }
        });
    }
}