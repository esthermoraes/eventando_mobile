package sofia.lorena.esther.eventando.menu.criar_evento;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

import sofia.lorena.esther.eventando.R;
import sofia.lorena.esther.eventando.acessar.CadastroActivity;
import sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_online.CriarEventOnlineFragment;
import sofia.lorena.esther.eventando.menu.criar_evento.criar_evento_presencial.CriarEventPresencialFragment;
import sofia.lorena.esther.eventando.menu.perfil.ProfileNaoEditavelFragment;
import sofia.lorena.esther.eventando.model.CriarEventViewModel;
import sofia.lorena.esther.eventando.model.HomeViewModel;
import sofia.lorena.esther.eventando.model.ViewEventViewModel;
import sofia.lorena.esther.eventando.util.Util;

public class CriarEventActivity extends AppCompatActivity {

    static int RESULT_TAKE_PICTURE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);
        setTitle("CRIAR EVENTO");

        CriarEventViewModel criarEventViewModel = new ViewModelProvider(this).get(CriarEventViewModel.class);

        RadioGroup radioGroup = findViewById(R.id.rdFormato);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.btnPresencial){
                    CriarEventPresencialFragment criarEventPresencialFragment = new CriarEventPresencialFragment();
                    setFragment(criarEventPresencialFragment, R.id.flInfoBasicas);
                }
                else{
                    CriarEventOnlineFragment criarEventOnlineFragment = new CriarEventOnlineFragment();
                    setFragment(criarEventOnlineFragment, R.id.flInfoBasicas);
                }
            }
        });


        final CriarEventViewModel vm = new ViewModelProvider(this).get(CriarEventViewModel.class);
        int optionsSelected = vm.getSelectedEventType();
        if(optionsSelected == 0){
            RadioButton btnPresencial = findViewById(R.id.btnPresencial);
            btnPresencial.setChecked(true);
        }
        else{
            RadioButton btnOnline = findViewById(R.id.btnOnline);
            btnOnline.setChecked(true);
        }

    }

    public void setFragment(Fragment fragment, int frameLayoutId) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(frameLayoutId, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void cadastrarEventoPresencial(String cep, int estado, String cidade, String bairro, int tipoLogradouro, String logradouro, String numero, Button btnCriar){

        CriarEventViewModel criarEventViewModel = new ViewModelProvider(this).get(CriarEventViewModel.class);

        btnCriar.setEnabled(false);

        String currentPhotoPath = criarEventViewModel.getCurrentPhotoPath();
        if(!currentPhotoPath.isEmpty()) {
            ImageView imFotoEvento = findViewById(R.id.imFotoEvento);
            // aqui carregamos a foto que está guardada dentro do arquivo currentPhotoPath dentro
            // de um objeto do tipo Bitmap. A imagem é carregada e sofre uma escala pra ficar
            // exatamente do tamanho do ImageView
            Bitmap bitmap = Util.getBitmap(currentPhotoPath, imFotoEvento.getWidth(), imFotoEvento.getHeight());
            imFotoEvento.setImageBitmap(bitmap);
        }

        EditText etNomeEvento =  findViewById(R.id.etNomeEvento);
        final String newetNomeEvento = etNomeEvento.getText().toString();
        if(newetNomeEvento.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de nome não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        //ToggleButton tbPrivacidade = findViewById(R.id.tbPrivacidade);

        EditText etObjetivoC =  findViewById(R.id.etObjetivoC);
        final String newetObjetivoC = etObjetivoC.getText().toString();
        if(newetObjetivoC.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de objetivo não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etDataPrevistaC =  findViewById(R.id.etDataPrevistaC);
        final String newetDataPrevistaC = etDataPrevistaC.getText().toString();
        if(newetDataPrevistaC.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de data prevista não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etHorarioC =  findViewById(R.id.etHorarioC);
        final String newetHorarioC = etDataPrevistaC.getText().toString();
        if(newetHorarioC.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de horario não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        String currentPhotoPath = criarEventViewModel.getCurrentPhotoPath();
        if(currentPhotoPath.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "O campo Foto do evento não foi preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        // Neste ponto, já verificamos que todos os campos foram preenchidos corretamente.
        // Antes enviar esses dados ao servidor, nós fazemos uma escala na imagem escolhida
        // para o produto. Fazemos isso porque a câmera do celular produz imagens muito grandes,
        // com resolução muito mais alta do que aquela que realmente precisamos. Logo, na
        // prática, o que fazemos aqui é diminuir o tamanho da imagem antes de enviá-la ao
        // servidor. Isso garante que será usado menos recurso de rede e de banco de dados
        // no servidor.
        //
        // A imagem é escalada de forma que sua altura fique em 300dp (tamanho do ImageView
        // que exibe os detalhes de um produto. A largura vai possuir
        // um tamanho proporcional ao tamamnho original.
        try {
            int h = (int) getResources().getDimension(R.dimen.img_height);
            Util.scaleImage(currentPhotoPath, -1, 2*h);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        EditText etCepCP =  findViewById(R.id.etCepCP);
        final String newetCepCP = etNomeEvento.getText().toString();
        if(newetCepCP.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de CEP não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        Spinner spEstadoCadastroCP = (Spinner)findViewById(R.id.spEstadoCadastroCP);
        int position = spEstadoCadastroCP.getSelectedItemPosition();
        if(position == 0) {
            Toast.makeText(CriarEventActivity.this, "Campo de estado não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etCidadeCP =  findViewById(R.id.etCidadeCP);
        final String newetCidadeCP = etNomeEvento.getText().toString();
        if(newetCidadeCP.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de cidade não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etBairroCP =  findViewById(R.id.etBairroCP);
        final String newetBairroCP = etNomeEvento.getText().toString();
        if(newetBairroCP.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de bairro não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        Spinner spTipoLogradouro = (Spinner)findViewById(R.id.spTipoLogradouro);
        int position2 = spTipoLogradouro.getSelectedItemPosition();
        if(position2 == 0) {
            Toast.makeText(CriarEventActivity.this, "Campo de tipo logradouro não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etLogradouroCP =  findViewById(R.id.etLogradouroCP);
        final String newetLogradouroCP = etNomeEvento.getText().toString();
        if(newetLogradouroCP.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de logradouro não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etNumeroCP =  findViewById(R.id.etNumeroCP);
        final String newetNumeroCP = etNomeEvento.getText().toString();
        if(newetNumeroCP.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de numero não preenchido", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void cadastrarEventoOnline(int plataforma, String link){
        EditText etNomeEvento =  findViewById(R.id.etNomeEvento);
        final String newetNomeEvento = etNomeEvento.getText().toString();
        if(newetNomeEvento.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de nome não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        //ToggleButton tbPrivacidade = findViewById(R.id.tbPrivacidade);

        EditText etObjetivoC =  findViewById(R.id.etObjetivoC);
        final String newetObjetivoC = etObjetivoC.getText().toString();
        if(newetObjetivoC.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de objetivo não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etDataPrevistaC =  findViewById(R.id.etDataPrevistaC);
        final String newetDataPrevistaC = etDataPrevistaC.getText().toString();
        if(newetDataPrevistaC.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de data prevista não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etHorarioC =  findViewById(R.id.etHorarioC);
        final String newetHorarioC = etDataPrevistaC.getText().toString();
        if(newetHorarioC.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de horario não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        //Image Button imFotoEvento = findViewById(R.id.imFotoEvento);

        Spinner spPlataformaO = (Spinner)findViewById(R.id.spPlataformaO);
        int position2 = spPlataformaO.getSelectedItemPosition();
        if(position2 == 0) {
            Toast.makeText(CriarEventActivity.this, "Campo de plataforma não preenchido", Toast.LENGTH_LONG).show();
            return;
        }

        EditText etLinkCO =  findViewById(R.id.etLinkCO);
        final String newetLinkCO = etNomeEvento.getText().toString();
        if(newetLinkCO.isEmpty()) {
            Toast.makeText(CriarEventActivity.this, "Campo de link não preenchido", Toast.LENGTH_LONG).show();
            return;
        }
    }

    private void dispatchGalleryOrCameraIntent() {

        // Primeiro, criamos o arquivo que irá guardar a imagem.
        File f = null;
        try {
            f = createImageFile();
        } catch (IOException e) {
            Toast.makeText(CriarEventActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }

        // Se o arquivo foi criado com sucesso...
        if(f != null) {

            // setamos o endereço do arquivo criado dentro do ViewModel
            CriarEventViewModel criarEventViewModel = new ViewModelProvider(this).get(CriarEventViewModel.class);
            criarEventViewModel.setCurrentPhotoPath(f.getAbsolutePath());

            // Criamos e configuramos o INTENT que dispara a câmera
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fUri = FileProvider.getUriForFile(CriarEventActivity.this, "sofia.lorena.esther.eventando.fileprovider", f);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fUri);

            // Criamos e configuramos o INTENT que dispara a escolha de imagem via galeria
            Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            galleryIntent.setType("image/*");

            // Criamos o INTENT que gera o menu de escolha. Esse INTENT contém os dois INTENTS
            // anteriores e permite que o usuário esolha entre câmera e galeria de fotos.
            Intent chooserIntent = Intent.createChooser(galleryIntent, "Pegar imagem de...");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { cameraIntent });
            startActivityForResult(chooserIntent, RESULT_TAKE_PICTURE);
        }
        else {
            Toast.makeText(CriarEventActivity.this, "Não foi possível criar o arquivo", Toast.LENGTH_LONG).show();
            return;
        }
    }

    /**
     * Método que cria um arquivo vazio, onde será guardada a imagem escolhida. O arquivo é
     * criado dentro do espaço interno da app, no diretório PICTURES. O nome do arquivo usa a
     * data e hora do momento da criação do arquivo. Isso garante que sempre que esse método for
     * chamado, não haverá risco de sobrescrever o arquivo anterior.
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File f = File.createTempFile(imageFileName, ".jpg", storageDir);
        return f;
    }

    /**
     * Esse método é chamado depois que o usuário escolhe a foto
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_TAKE_PICTURE) {

            // Pegamos o endereço do arquivo vazio que foi criado para guardar a foto escolhida
            CriarEventViewModel criarEventViewModel = new ViewModelProvider(this).get(CriarEventViewModel.class);
            String currentPhotoPath = criarEventViewModel.getCurrentPhotoPath();

            // Se a foto foi efetivamente escolhida pelo usuário...
            if(resultCode == Activity.RESULT_OK) {
                ImageView imFotoEvento = findViewById(R.id.imFotoEvento);

                // se o usuário escolheu a câmera, então quando esse método é chamado, a foto tirada
                // já está salva dentro do arquivo currentPhotoPath. Entretanto, se o usuário
                // escolheu uma foto da galeria, temos que obter o URI da foto escolhida:
                Uri selectedPhoto = data.getData();
                if(selectedPhoto != null) {
                    try {
                        // carregamos a foto escolhida em um bitmap
                        Bitmap bitmap = Util.getBitmap(this, selectedPhoto);
                        // salvamos o bitmao dentro do arquivo currentPhotoPath
                        Util.saveImage(bitmap, currentPhotoPath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        return;
                    }
                }

                // Carregamos a foto salva em currentPhotoPath com a escala correta e setamos no ImageView
                Bitmap bitmap = Util.getBitmap(currentPhotoPath, imFotoEvento.getWidth(), imFotoEvento.getHeight());
                imFotoEvento.setImageBitmap(bitmap);
            }
            else {
                // Se a imagem não foi escolhida, deletamos o arquivo que foi criado para guardá-la
                File f = new File(currentPhotoPath);
                f.delete();
                criarEventViewModel.setCurrentPhotoPath("");
            }
        }
    }



}