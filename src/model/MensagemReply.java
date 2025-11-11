package model;

import java.io.Serializable;
import java.util.List;

/**
 * Mensagem de resposta do Servidor para o Cliente
 * Implementa Serializable para ser empacotada/desempacotada
 */
public class MensagemReply implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private boolean sucesso;
    private String mensagem;
    private Fatura fatura;
    private List<Fatura> faturas;
    
    public MensagemReply() {}
    
    public MensagemReply(boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }
    
    public MensagemReply(boolean sucesso, String mensagem, List<Fatura> faturas) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.faturas = faturas;
    }
    
    public boolean isSucesso() { return sucesso; }
    public void setSucesso(boolean sucesso) { this.sucesso = sucesso; }
    
    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }
    
    public Fatura getFatura() { return fatura; }
    public void setFatura(Fatura fatura) { this.fatura = fatura; }
    
    public List<Fatura> getFaturas() { return faturas; }
    public void setFaturas(List<Fatura> faturas) { this.faturas = faturas; }
    
    @Override
    public String toString() {
        return "MensagemReply{sucesso=" + sucesso + ", mensagem='" + mensagem + 
               "', faturas=" + (faturas != null ? faturas.size() : 0) + "}";
    }
}
