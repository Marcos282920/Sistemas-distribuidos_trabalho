public class Secretaria extends Servico{
     private String mensagem;
     private int limite_mensagem;
     private String mensagem_linha;

    public Secretaria(String tipo, double preço, String mensagem, int capacidade, String linha) {
        super(tipo, preço);
        this.mensagem = mensagem;
        this.limite_mensagem = capacidade;
        this.mensagem_linha = linha;
    }

    public void setMessagem(String mensagem){
         this.mensagem = mensagem;
    }

    public String getMensagem(){
         return mensagem;
    }

    public void setLimite_mensagem(int capacidade){
        this.limite_mensagem = capacidade;
    }

    public int getLimite_mensagem(){
        return limite_mensagem;
    }

    public void setMensagem_linha(String linha){
        this.mensagem_linha = linha;
    }

    public String getMensagem_linha(){
        return mensagem_linha;
    }





}
