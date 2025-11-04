public class Agenda extends Servico{

    private String nome_contato;
    private String numero_contato;

    public Agenda(String tipo, double preço, String nomecontato, String numerocontato) {
        super(tipo, preço);
        this.nome_contato = nomecontato;
        this.numero_contato = numerocontato;
    }

    public void setNome_contato(String nomecontato){
        this.nome_contato = nomecontato;
    }

    public String getNome_contato(){
        return nome_contato;
    }

    public void setNumero_contato(String numerocontato){
        this.numero_contato = numerocontato;
    }

    public String getNumero_contato(){
        return numero_contato;
    }
}
