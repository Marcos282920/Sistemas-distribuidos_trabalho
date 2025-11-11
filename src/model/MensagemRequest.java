package model;

import java.io.Serializable;
import java.util.List;

/**
 * Mensagem de requisição do Cliente para o Servidor
 * Implementa Serializable para ser empacotada/desempacotada
 */
public class MensagemRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private TipoOperacao operacao;
    private String numeroLinha;
    private Fatura fatura;
    private List<Fatura> faturas;
    
    public enum TipoOperacao {
        OBTER_FATURAS,
        OBTER_FATURA_POR_LINHA,
        CRIAR_FATURA,
        PAGAR_FATURA,
        LISTAR_TODAS
    }
    
    public MensagemRequest() {}
    
    public MensagemRequest(TipoOperacao operacao) {
        this.operacao = operacao;
    }
    
    public MensagemRequest(TipoOperacao operacao, String numeroLinha) {
        this.operacao = operacao;
        this.numeroLinha = numeroLinha;
    }
    
    public TipoOperacao getOperacao() { return operacao; }
    public void setOperacao(TipoOperacao operacao) { this.operacao = operacao; }
    
    public String getNumeroLinha() { return numeroLinha; }
    public void setNumeroLinha(String numeroLinha) { this.numeroLinha = numeroLinha; }
    
    public Fatura getFatura() { return fatura; }
    public void setFatura(Fatura fatura) { this.fatura = fatura; }
    
    public List<Fatura> getFaturas() { return faturas; }
    public void setFaturas(List<Fatura> faturas) { this.faturas = faturas; }
    
    @Override
    public String toString() {
        return "MensagemRequest{operacao=" + operacao + ", linha='" + numeroLinha + "'}";
    }
}
