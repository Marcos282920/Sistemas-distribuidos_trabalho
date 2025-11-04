public class Siga extends Servico{
     private String numero_origem;
     private String numero_destino;

    public Siga(String tipo, double preço, String numerod, String numeroo) {
        super(tipo, preço);
        this.numero_destino = numerod;
        this.numero_origem = numeroo;
    }

    public void setNumero_origem(String numeroo){
        this.numero_origem = numeroo;
    }

    public String getNumero_origem(){
        return numero_origem;
    }

    public void setNumero_destino(String numerod){
        this.numero_destino = numerod;
    }

    public String getNumero_destino(){
        return numero_destino;
    }


}
