package sofia.lorena.esther.eventando;

import android.content.Context;
import android.util.Log;

import com.example.produtos.util.Config;
import com.example.produtos.util.HttpRequest;
import com.example.produtos.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EventsRepository {

    /**
     * Essa classe concentra todos os métodos de conexão entre a app e o servidor web
     */
    Context context;
    public ProductsRepository(Context context) {
        this.context = context;
    }

    /**
     * Método que cria uma requisição HTTP para registrar um novo usuário junto ao servidor web.
     * @param newLogin o login do novo usuário
     * @param newPassword a senha do novo usuário
     * @return true se o usuário foi cadastrado e false caso contrário
     */
    public boolean register(String newLogin, String newPassword) {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.PRODUCTS_APP_URL + "registrar.php", "POST", "UTF-8");
        httpRequest.addParam("novo_login", newLogin);
        httpRequest.addParam("nova_senha", newPassword);

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
            if(success == 1) {
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

    /**
     * Método que cria uma requisição HTTP para autenticar um usuário junto ao servidor web.
     * @param login o login do usuário
     * @param password a senha do usuário
     * @return true se o usuário  foi autenticado, false caso contrário
     */
    public boolean login(String login, String password) {

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.PRODUCTS_APP_URL + "login.php", "POST", "UTF-8");
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
            if(success == 1) {
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

    /**
     * Método que cria uma requisição HTTP para cadastrar um novo produto junto ao servidor web.
     * @param name nome do produto
     * @param price preço do produto
     * @param description descrição do produto
     * @param imgLocation endereço do arquivo que contém a imagem do produto
     * @return true se o produto foi cadastrado junto ao servidor, false caso contrário
     */
    public boolean addProduct(String name, String price, String description, String imgLocation) {

        // Para cadastrar um produto, é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.PRODUCTS_APP_URL + "criar_produto.php", "POST", "UTF-8");
        httpRequest.addParam("nome", name);
        httpRequest.addParam("preco", price);
        httpRequest.addParam("descricao", description);
        httpRequest.addFile("img", new File(imgLocation));

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

    /**
     * Método que cria uma requisição HTTP para obter uma página/bloco de produtos junto ao servidor web.
     * @param limit a quantidade de produtos a serem obtidos
     * @param offSet a posição a partir da qual a página de produtos deve começar
     * @return lista de produtos
     */
    public List<Product> loadProducts(Integer limit, Integer offSet) {

        // cria a lista de produtos incicialmente vazia, que será retornada como resultado
        List<Product> productsList = new ArrayList<>();

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.PRODUCTS_APP_URL +"pegar_produtos.php", "GET", "UTF-8");
        httpRequest.addParam("limit", limit.toString());
        httpRequest.addParam("offset", offSet.toString());

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
                JSONArray jsonArray = jsonObject.getJSONArray("produtos");

                // Cada elemento do JSONArray é um JSONObject que guarda os dados de um produto
                for(int i = 0; i < jsonArray.length(); i++) {

                    // Obtemos o JSONObject referente a um produto
                    JSONObject jProduct = jsonArray.getJSONObject(i);

                    // Obtemos os dados de um produtos via JSONObject
                    String pid = jProduct.getString("id");
                    String name = jProduct.getString("nome");
                    String price = jProduct.getString("preco");
                    String img = jProduct.getString("img");

                    // Criamo um objeto do tipo Product para guardar esses dados
                    Product product = new Product();
                    product.id = pid;
                    product.name = name;
                    product.price = price;
                    product.img = img;

                    // Adicionamos o objeto product na lista de produtos
                    productsList.add(product);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("HTTP RESULT", result);
        }

        return productsList;
    }

    /**
     * Método que cria uma requisição HTTP para obter os detalhes de um produto junto ao servidor web.
     * @param id id do produto que se deseja obter os detalhes
     * @return objeto do tipo product contendo os detalhes do produto
     */
    Product loadProductDetail(String id) {

        // Para obter a lista de produtos é preciso estar logado. Então primeiro otemos o login e senha
        // salvos na app.
        String login = Config.getLogin(context);
        String password = Config.getPassword(context);

        // Cria uma requisição HTTP a adiona o parâmetros que devem ser enviados ao servidor
        HttpRequest httpRequest = new HttpRequest(Config.PRODUCTS_APP_URL + "pegar_detalhes_produto.php", "GET", "UTF-8");
        httpRequest.addParam("id", id);

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
                String price = jsonObject.getString("preco");
                String img = jsonObject.getString("img");
                String description = jsonObject.getString("descricao");
                String createdBy = jsonObject.getString("criado_por");
                String createdAt = jsonObject.getString("criado_em");

                // Cria um objeto Product e guarda os detalhes do produto dentro dele.
                Product p = new Product();
                p.name = name;
                p.id = id;
                p.price = price;
                p.img = img;
                p.description = description;
                p.createdBy = createdBy;
                p.createdAt = createdAt;

                return p;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}


