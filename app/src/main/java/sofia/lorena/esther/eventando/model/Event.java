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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Event otherEvent = (Event) obj;

        // Comparar os campos relevantes para determinar se os objetos são iguais
        return id == otherEvent.id && nome.equals(otherEvent.nome) && data.equals(otherEvent.data) && objetivo.equals(otherEvent.objetivo);
    }
}
