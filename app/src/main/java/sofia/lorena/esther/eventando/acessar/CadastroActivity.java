package sofia.lorena.esther.eventando.acessar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.model.CadastroViewModel;

public class CadastroActivity extends AppCompatActivity {

    CadastroViewModel cadastroViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        // obtemos o ViewModel pois é nele que está o método que se conecta ao servior web.
        cadastroViewModel = new ViewModelProvider(this).get(CadastroViewModel.class);

        // Quando o usuário clicar no bptão cadastrar
        Button btnCadastrar =  findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Primeiro verificamos se o usuário digitou corretamente os dados de cadastro.
                // No nosso caso, apenas verificamos se o campo não está vazio no momento em que o
                // usuário clicou no botão cadastrar. Se o campo está vazio, exibimos uma mensagem para o
                // usuário indicando que ele não preencheu o campo e retornamos da função sem fazer
                // mais nada.
                EditText etNomeCadastro =  findViewById(R.id.etNomeCadastro);
                final String newetNomeCadastro = etNomeCadastro.getText().toString();
                if(newetNomeCadastro.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de nome não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText dtDataNasc =  findViewById(R.id.dtDataNasc);
                final String newdtDataNasc = dtDataNasc.getText().toString();
                if(newdtDataNasc.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de data de nascimento não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                Spinner spEstadoCadastro = (Spinner)findViewById(R.id.spEstadoCadastro);
                int position = spEstadoCadastro.getSelectedItemPosition();
                if(position == 0) {
                    Toast.makeText(CadastroActivity.this, "Campo de estado não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText phTelefoneCadastro = findViewById(R.id.phTelefoneCadastro);
                final String newphTelefoneCadastro = phTelefoneCadastro.getText().toString();
                if(newphTelefoneCadastro.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de telefone não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText emEmailCadastro = findViewById(R.id.emEmailCadastro);
                final String newemEmailCadastro = emEmailCadastro.getText().toString();
                if(newemEmailCadastro.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de email não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText pwdSenhaCadastro =  findViewById(R.id.pwdSenhaCadastro);
                final String newpwdSenhaCadastro= pwdSenhaCadastro.getText().toString();
                if(newpwdSenhaCadastro.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText pwdConfSenhaCadastro =  findViewById(R.id.pwdConfSenhaCadastro);
                String newpwdConfSenhaCadastro = pwdConfSenhaCadastro.getText().toString();
                if(newpwdConfSenhaCadastro.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Campo de checagem de senha não preenchido", Toast.LENGTH_LONG).show();
                    return;
                }

                if(!newpwdSenhaCadastro.equals(newpwdConfSenhaCadastro)) {
                    Toast.makeText(CadastroActivity.this, "Senha não confere", Toast.LENGTH_LONG).show();
                    return;
                }

                // O ViewModel possui o método register, que envia as informações para o servidor web.
                // O servidor web recebe as infos e cadastra um novo usuário. Se o usuário foi cadastrado
                // com sucesso, a app recebe o valor true. Se não o servidor retorna o valor false.
                //
                // O método de register retorna um LiveData, que na prática é um container que avisa
                // quando o resultado do servidor chegou.
                LiveData<Boolean> resultLD = cadastroViewModel.register(newetNomeCadastro, newdtDataNasc, position, newphTelefoneCadastro, newemEmailCadastro, newpwdSenhaCadastro);

                // Aqui nós observamos o LiveData. Quando o servidor responder, o resultado indicando
                // se o cadastro deu certo ou não será guardado dentro do LiveData. Neste momento o
                // LiveData avisa que o resultado chegou chamando o método onChanged abaixo.
                resultLD.observe(CadastroActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        // aBoolean contém o resultado do cadastro. Se aBoolean for true, significa
                        // que o cadastro do usuário foi feito corretamente. Indicamos isso ao usuário
                        // através de uma mensagem do tipo toast e finalizamos a Activity. Quando
                        // finalizamos a Activity, voltamos para a tela de login.
                        if(aBoolean) {
                            Toast.makeText(CadastroActivity.this, "Novo usuario registrado com sucesso", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            // Se o cadastro não deu certo, apenas continuamos na tela de cadastro e
                            // indicamos com uma mensagem ao usuário que o cadastro não deu certo.
                            Toast.makeText(CadastroActivity.this, "Erro ao registrar novo usuário", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}