package sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial;

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
 * Use the {@link CriarEventPresencialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CriarEventPresencialFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    CriarEventActivity criarEventActivity;

    public CriarEventPresencialFragment(CriarEventActivity criarEventActivity) {
        // Required empty public constructor
        this.criarEventActivity = criarEventActivity;
    }

    // TODO: Rename and change types and number of parameters
    public static CriarEventPresencialFragment newInstance(CriarEventActivity criarEventActivity) {
        CriarEventPresencialFragment fragment = new CriarEventPresencialFragment(criarEventActivity);
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
        return inflater.inflate(R.layout.fragment_events_criar_presencial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCriar = view.findViewById(R.id.btnCriarP);
        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCriar.setEnabled(false);

                EditText etCepCP = view.findViewById(R.id.etCepCP);
                final String newetCepCP = etCepCP.getText().toString();

                if (newetCepCP.isEmpty()) {
                    Toast.makeText(getContext(), "Campo de CEP não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }

                Spinner spEstadoCadastroCP = (Spinner) view.findViewById(R.id.spEstadoCadastroCP);
                int position = spEstadoCadastroCP.getSelectedItemPosition();
                if (position == 0) {
                    Toast.makeText(getContext(), "Campo de estado não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }

                EditText etCidadeCP =  view.findViewById(R.id.etCidadeCP);
                final String newetCidadeCP = etCidadeCP.getText().toString();
                if(newetCidadeCP.isEmpty()) {
                    Toast.makeText(getContext(), "Campo de cidade não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }

                EditText etBairroCP =  view.findViewById(R.id.etBairroCP);
                final String newetBairroCP = etBairroCP.getText().toString();
                if(newetBairroCP.isEmpty()) {
                    Toast.makeText(getContext(), "Campo de bairro não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }

                Spinner spTipoLogradouro = (Spinner)view.findViewById(R.id.spTipoLogradouroCP);
                int position2 = spTipoLogradouro.getSelectedItemPosition();
                if(position2 == 0) {
                    Toast.makeText(getContext(), "Campo de tipo logradouro não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }

                EditText etLogradouroCP =  view.findViewById(R.id.etLogradouroCP);
                final String newetLogradouroCP = etLogradouroCP.getText().toString();
                if(newetLogradouroCP.isEmpty()) {
                    Toast.makeText(getContext(), "Campo de logradouro não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }

                EditText etNumeroCP =  view.findViewById(R.id.etNumeroCP);
                final String newetNumeroCP = etNumeroCP.getText().toString();
                if(newetNumeroCP.isEmpty()) {
                    Toast.makeText(getContext(), "Campo de número não preenchido", Toast.LENGTH_LONG).show();
                    btnCriar.setEnabled(true);
                    return;
                }
                criarEventActivity.cadastrarEventoPresencial(newetCepCP, position, newetCidadeCP, newetBairroCP, position2, newetLogradouroCP, newetNumeroCP, btnCriar );
            }
        });
    }
}