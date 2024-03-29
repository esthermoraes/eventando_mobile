package sofia.lorena.esther.eventando.model;

/**
 * Classe para representar um produto
 */
public class Event {

    public String privacidade;
    public String id; // id do Evento
    public String idcriadorEvento; // quem criou o evento

    public String nome;
    public String objetivo;

    public String data; // OLHAR DEPOIS DATE
    public String hora; // OLHAR DEPOIS TIME

    public String imagem;
    public String formato;
    public String atracoes;

    public String tipo_contato;

    public String contato;

    private boolean favorito;

    public String getId() {
        return id;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String img() {
        return imagem;
    }

    public String formato() {
        return formato;
    }

    public String nome() {
        return nome;
    }

    public String data() {
        return data;
    }

    public void setId(String pid) {
        this.id = id;
    }
}
