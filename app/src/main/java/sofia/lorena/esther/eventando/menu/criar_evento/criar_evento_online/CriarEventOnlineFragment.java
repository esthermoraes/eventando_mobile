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


    // TODO: Rename and change types and number of parameters
    public static CriarEventOnlineFragment newInstance(CriarEventActivity criarEventActivity) {
        CriarEventOnlineFragment fragment = new CriarEventOnlineFragment(criarEventActivity);
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
                Spinner spPlataformaO = view.findViewById(R.id.spPlataformaO);

                if (spPlataformaO != null) {
                    int position = spPlataformaO.getSelectedItemPosition();

                    EditText etLinkCO = view.findViewById(R.id.etLinkCO);
                    final String newetLinkCO = etLinkCO.getText().toString();

                    if (position != Spinner.INVALID_POSITION) {
                        // posição válida
                        if (newetLinkCO.isEmpty()) {
                            Toast.makeText(requireContext(), "Campo de link não preenchido", Toast.LENGTH_LONG).show();
                            btnCriar.setEnabled(true);
                            return;
                        }

                        criarEventActivity.cadastrarEventoOnline(position, newetLinkCO, btnCriar);
                    } else {
                        // posição inválida
                        Toast.makeText(requireContext(), "Campo de plataforma não preenchido", Toast.LENGTH_LONG).show();
                        btnCriar.setEnabled(true);
                    }
                }
            }
        });
    }
}