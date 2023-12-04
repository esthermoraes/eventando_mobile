package sofia.lorena.esther.eventando.model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import sofia.lorena.esther.eventando.util.Config;
import sofia.lorena.esther.eventando.util.HttpRequest;
import sofia.lorena.esther.eventando.util.Util;

/**
 * Essa classe concentra todos os métodos de conexão entre a app e o servidor web
 */
public class EventandoRepository {

    Context context;

    public EventandoRepository(Context context) {
        this.context = context;
    }


    public boolean register(String novo_nome, String nova_data_nasc, int novo_estado, String novo_telefone, String novo_email, String nova_senha) {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.EVENTS_APP_URL + "registrar.php", "POST", "UTF-8");
        httpRequest.addParam("novo_nome", novo_nome);
        httpRequest.addParam("nova_data_nasc", nova_data_nasc);
        httpRequest.addParam("novo_estado", Integer.toString(novo_estado));
        httpRequest.addParam("novo_telefone", (novo_telefone));
        httpRequest.addParam("novo_email", novo_email);
        httpRequest.addParam("nova_senha", nova_senha);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0, "erro":"usuario já existe"}
            result = Util.inputStream2String(is, "UTF-8");

            Log.i("HTTP REGISTER RESULT", result);

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, significa que o usuário foi registrado com sucesso.
            if (success == 1) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }
        return false;

    }

    public boolean login(String email, String senha) {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.EVENTS_APP_URL + "logar.php", "POST", "UTF-8");
        httpRequest.setBasicAuth(email, senha);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0, "erro":"usuario ou senha não confere"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP LOGIN RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, significa que o usuário foi autenticado com sucesso.
            if (success == 1) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }
        return false;
    }

    Event loadEventDetail(String id) {return null;}

    public UserProfile loadProfileDetails() {

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.EVENTS_APP_URL + "pegar_detalhes_usuario.php", "GET", "UTF-8");
        httpRequest.addParam("email", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,"nome":"produto 1","preco":"10.00", "img":"www.imgur.com/img1.jpg", "descricao":"produto 1","criado_em":"2022-10-03 19:43:31.42905","criado_por":"daniel"}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter detalhes do produto"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP DETAILS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os detalhes do produto são obtidos da String JSON e um objeto
            // do tipo Product é criado para guardar esses dados
            if(success == 1) {

                // obtém os dados detalhados do produto. A imagem não vem junto. Ela é obtida
                // separadamente depois, no momento em que precisa ser exibida na app. Isso permite
                // que os dados trafeguem mais rápido.
                String name = jsonObject.getString("nome");
                String email = jsonObject.getString("email");
                String data_nasc = jsonObject.getString("data_nasc");
                String estado = jsonObject.getString("estado");
                String telefone = jsonObject.getString("telefone");

                // Cria um objeto Product e guarda os detalhes do produto dentro dele.
                UserProfile p = new UserProfile();
                p.nome = name;
                p.email = email;
                p.dataNascimento = data_nasc;
                p.estado = estado;
                p.telefone = telefone;
                return p;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String criarEventoPresencial(String nome, String objetivo, String data, String hora, String imagem, String numero, int tipo_logradouro, String logradouro, String bairro_evento, String cidade_evento, int estado_evento, String cep, String privacidade) {

        // Para cadastrar um produto, é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.EVENTS_APP_URL + "criar_evento_presencial.php", "POST", "UTF-8");
        httpRequest.addParam("nome_evento", nome);
        httpRequest.addParam("objetivo_evento", objetivo);
        httpRequest.addParam("data_prevista_evento", data);
        httpRequest.addParam("horario_evento", hora);
        httpRequest.addFile("img_evento", new File(imagem));
        httpRequest.addParam("numero_evento", numero);
        httpRequest.addParam("tipo_logradouro", Integer.toString(tipo_logradouro));
        httpRequest.addParam("logradouro_evento", logradouro);
        httpRequest.addParam("bairro_evento", bairro_evento);
        httpRequest.addParam("cidade_evento", cidade_evento);
        httpRequest.addParam("estado_evento", Integer.toString(estado_evento));
        httpRequest.addParam("cep_evento", cep);
        httpRequest.addParam("privacidade_evento", privacidade);
        httpRequest.addParam("criador_evento", login);


        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);


        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1, "id":30}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao criar produto"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP ADD PRODUCT RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, significa que o produto foi adicionado com sucesso.
            if(success == 1) {
                return jsonObject.getString("id");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }
        return "";
    }

    public String criarEventoOnline(String nome, String objetivo, String data, String hora, String imagem, String link_evento, int plataforma_evento, String privacidade) {

        // Para cadastrar um produto, é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.EVENTS_APP_URL + "criar_evento_online.php", "POST", "UTF-8");
        httpRequest.addParam("nome_evento", nome);
        httpRequest.addParam("objetivo_evento", objetivo);
        httpRequest.addParam("data_prevista_evento", data);
        httpRequest.addParam("horario_evento", hora);
        httpRequest.addFile("img_evento", new File(imagem));
        httpRequest.addParam("link_evento", link_evento);
        httpRequest.addParam("plataforma_evento", Integer.toString(plataforma_evento));
        httpRequest.addParam("privacidade_evento", privacidade);
        httpRequest.addParam("criador_evento", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);



        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1}
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao criar produto"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP ADD PRODUCT RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, significa que o produto foi adicionado com sucesso.
            if(success == 1) {
                return jsonObject.getString("id");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }
        return "";
    }

    public List<Event> loadEvents(Integer limit, Integer offSet) {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado
        List<Event> eventArrayList = new ArrayList<>();

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.EVENTS_APP_URL +"pegar_eventos_feed.php", "GET", "UTF-8");
        httpRequest.addParam("limit", limit.toString());
        httpRequest.addParam("offset", offSet.toString());
        httpRequest.addParam("user_email", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,
            //  "produtos":[
            //          {"id":"7", "nome":"produto 1", "preco":"10.00", "img":"www.imgur.com/img1.jpg"},
            //          {"id":"8", "nome":"produto 2", "preco":"20.00", "img":"www.imgur.com/img2.jpg"}
            //       ]
            // }
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter produtos"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArray = jsonObject.getJSONArray("eventos");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArray.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jEvent = jsonArray.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String pid = jEvent.getString("id");
                    String nome = jEvent.getString("nome");
                    String objetivo = jEvent.getString("objetivo");
                    String data = jEvent.getString("data_prevista");
                    String img = jEvent.getString("img");

                    // Criamo um objeto do tipo Product para guardar esses dados
                    Event event = new Event();
                    event.id = pid;
                    event.nome = nome;
                    event.objetivo = objetivo;
                    event.data = data;
                    event.imagem = img;

                    // Adicionamos o objeto product na lista de produtos
                    eventArrayList.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return eventArrayList;
    }

    public List<Event> loadMyEvents(Integer limit, Integer offSet) {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado
        List<Event> myEventsArrayList = new ArrayList<>();

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.EVENTS_APP_URL +"pegar_meus_eventos.php", "GET", "UTF-8");
        httpRequest.addParam("limit", limit.toString());
        httpRequest.addParam("offset", offSet.toString());
        httpRequest.addParam("user_email", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,
            //  "produtos":[
            //          {"id":"7", "nome":"produto 1", "preco":"10.00", "img":"www.imgur.com/img1.jpg"},
            //          {"id":"8", "nome":"produto 2", "preco":"20.00", "img":"www.imgur.com/img2.jpg"}
            //       ]
            // }
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter produtos"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArray = jsonObject.getJSONArray("meus eventos");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArray.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jEvent = jsonArray.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String pid = jEvent.getString("id");
                    String nome = jEvent.getString("nome");
                    String objetivo = jEvent.getString("objetivo");
                    String data = jEvent.getString("data_prevista");
                    String img = jEvent.getString("img");

                    // Criamo um objeto do tipo Product para guardar esses dados
                    Event event = new Event();
                    event.id = pid;
                    event.nome = nome;
                    event.objetivo = objetivo;
                    event.data = data;
                    event.imagem = img;

                    // Adicionamos o objeto product na lista de produtos
                    myEventsArrayList.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return myEventsArrayList;
    }

    public List<Event> loadEventsDoMomento() {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado
        List<Event> eventsDoMomentoArrayList = new ArrayList<>();

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.EVENTS_APP_URL +"pegar_eventos_carrossel.php", "GET", "UTF-8");
        httpRequest.addParam("user_email", login);

        // Para esta ação, é preciso estar logado. Então na requisição HTTP setamos o login e senha do
        // usuário. Ao executar a requisição, o login e senha do usuário serão enviados ao servidor web,
        // o qual verificará se o login e senha batem com aquilo que está no BD. Somente depois dessa
        // verificação de autenticação é que o servidor web irá realizar esta ação.
        httpRequest.setBasicAuth(login, password);

        String result = "";
        try {
            // Executa a requisição HTTP. É neste momento que o servidor web é contactado. Ao executar
            // a requisição é aberto um fluxo de dados entre o servidor e a app (InputStream is).
            InputStream is = httpRequest.execute();

            // Obtém a resposta fornecida pelo servidor. O InputStream é convertido em uma String. Essa
            // String é a resposta do servidor web em formato JSON.
            //
            // Em caso de sucesso, será retornada uma String JSON no formato:
            //
            // {"sucesso":1,
            //  "produtos":[
            //          {"id":"7", "nome":"produto 1", "preco":"10.00", "img":"www.imgur.com/img1.jpg"},
            //          {"id":"8", "nome":"produto 2", "preco":"20.00", "img":"www.imgur.com/img2.jpg"}
            //       ]
            // }
            //
            // Em caso de falha, será retornada uma String JSON no formato:
            //
            // {"sucesso":0,"erro":"Erro ao obter produtos"}
            result = Util.inputStream2String(is, "UTF-8");

            // Fecha a conexão com o servidor web.
            httpRequest.finish();

            Log.i("HTTP PRODUCTS RESULT", result);

            // A classe JSONObject recebe como parâmetro do construtor uma String no formato JSON e
            // monta internamente uma estrutura de dados similar ao dicionário em python.
            JSONObject jsonObject = new JSONObject(result);

            // obtem o valor da chave sucesso para verificar se a ação ocorreu da forma esperada ou não.
            int success = jsonObject.getInt("sucesso");

            // Se sucesso igual a 1, os produtos são obtidos da String JSON e adicionados à lista de
            // produtos a ser retornada como resultado.
            if(success == 1) {

                // A chave produtos é um array de objetos do tipo json (JSONArray), onde cada um desses representa
                // um produto
                JSONArray jsonArray = jsonObject.getJSONArray("eventos do momento");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArray.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jEvent = jsonArray.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String pid = jEvent.getString("id");
                    String nome = jEvent.getString("nome");
                    String data = jEvent.getString("data_prevista");
                    String img = jEvent.getString("img");

                    // Criamo um objeto do tipo Product para guardar esses dados
                    Event event = new Event();
                    event.id = pid;
                    event.nome = nome;
                    event.data = data;
                    event.imagem = img;

                    // Adicionamos o objeto product na lista de produtos
                    eventsDoMomentoArrayList.add(event);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return eventsDoMomentoArrayList;
    }

}
