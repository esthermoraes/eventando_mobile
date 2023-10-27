package sofia.lorena.esther.eventando.menu.perfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.acessar.LoginActivity;
import sofia.lorena.esther.eventando.model.ProfileViewModel;
import sofia.lorena.esther.eventando.model.UserProfile;
import sofia.lorena.esther.eventando.util.Config;

public class ProfileNaoEditavelFragment extends Fragment {
    private ProfileViewModel profileViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_nao_editavel, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProfileViewModel vm = new ViewModelProvider(getActivity()).get(ProfileViewModel.class);

        LiveData<UserProfile> userProfileLD = vm.getUserProfile();

        userProfileLD.observe(getViewLifecycleOwner(), new Observer<UserProfile>() {
                    @Override
                    public void onChanged(UserProfile userProfile) {
                        TextView tvNome = view.findViewById(R.id.tvNomeNaoEd);
                        tvNome.setText(userProfile.nome);

                        TextView tvDataNascimento = view.findViewById(R.id.tvDataNasc);
                        tvDataNascimento.setText(userProfile.dataNascimento);

                        TextView tvEstado = view.findViewById(R.id.tvEstadoPerfil);
                        tvEstado.setText(userProfile.estado);

                        TextView tvEmail = view.findViewById(R.id.tvEmailCadastro);
                        tvEmail.setText(userProfile.email);


                        TextView tvTelefone = view.findViewById(R.id.phTelefoneCadastro);
                        tvTelefone.setText(userProfile.telefone);
                    }
                });



        // Inicialize o botão e defina o OnClickListener aqui
        Button btnEncerrarSessao = view.findViewById(R.id.btnEncerrarSess);

        btnEncerrarSessao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Limpe as informações de login (se necessário)
                Config.setLogin(requireContext(), "");

                // Redirecione o usuário para a tela de login
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}

