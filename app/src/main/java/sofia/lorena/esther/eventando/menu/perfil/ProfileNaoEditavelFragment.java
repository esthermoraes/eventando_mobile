package sofia.lorena.esther.eventando.menu.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.acessar.LoginActivity;

public class ProfileNaoEditavelFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_nao_editavel, container, false);

        Button btnEncerrarSessao = view.findViewById(R.id.btnEncerrarSess);
        btnEncerrarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encerrarSessao();
            }
        });

        return view;
    }

    private void encerrarSessao() {
        FirebaseAuth.getInstance().signOut();

        // Crie uma intent para ir para a tela de login
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        // Defina a ação da intent para iniciar uma nova tarefa e limpar a pilha de atividades
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Inicie a atividade de login
        startActivity(intent);
    }
}
